from flask import Flask, jsonify

import pandas as pd

from kmodes.kmodes import KModes # K-Modes
import joblib # Save and load model
from Preprocessing import * # Preprocess data
from sqlalchemy import create_engine
from K_Elbow import * # K-Elbow method
from sklearn.metrics import silhouette_score, davies_bouldin_score # Evaluation metrics

app = Flask(__name__)

# SQLAlchemy setup
DATABASE_URL = 'postgresql://postgres:Dangnguyen1809@100.69.153.113:5432/USTH_Connect'
engine = create_engine(DATABASE_URL)

# Path to the dataset
path = r"D:\\USTH\\Project\\usth-connect\\USTHConnect\\Flask\\dataset.csv"

# Function to load data from SQL database
def load_data_from_db(csv_file_path="study_buddy_data.csv"):
    # query = "SELECT * FROM study_buddy;"  # Replace with your actual SQL query
    query = """
        SELECT 
            sb.student_id,
            sb.name,
            sb.gender,
            sb.major,
            sb.personality,
            sb.communication_style,
            sb.looking_for,
            ARRAY_AGG(DISTINCT sbfs.subject) AS favorite_subjects,
            ARRAY_AGG(DISTINCT sbi.interest) AS interests,
            ARRAY_AGG(DISTINCT sbpp.place) AS preferred_places,
            ARRAY_AGG(DISTINCT sbpt.time) AS preferred_times
        FROM 
            study_buddy sb
        LEFT JOIN 
            study_buddy_favorite_subjects sbfs ON sb.student_id = sbfs.study_buddy_id
        LEFT JOIN 
            study_buddy_interests sbi ON sb.student_id = sbi.study_buddy_id
        LEFT JOIN 
            study_buddy_preferred_places sbpp ON sb.student_id = sbpp.study_buddy_id
        LEFT JOIN 
            study_buddy_preferred_times sbpt ON sb.student_id = sbpt.study_buddy_id
        GROUP BY 
            sb.student_id, sb.name, sb.gender, sb.personality, sb.communication_style, sb.looking_for;
    """
    df = pd.read_sql(query, engine)
    
    # Combine list-type fields into comma-separated strings
    fields_to_combine = ["favorite_subjects", "interests", "preferred_places", "preferred_times"]
    for field in fields_to_combine:
        df[field] = df[field].apply(lambda x: ", ".join(x) if isinstance(x, list) else "")
        
     # Rename columns for readability
    df = df.rename(columns={
        "name": "FullName",
        "major": "Major",
        "gender": "Gender",
        "personality": "Personality",
        "interests": "Interests",
        "communication_style": "Communication_Style",
        "looking_for": "Looking_for",
        "favorite_subjects": "Favorite_Subject",
        "preferred_places": "Study_Location",
        "preferred_times": "Study_Time"
    })

    # Save DataFrame to a CSV file
    df.to_csv(csv_file_path, index=False)

    print(f"Data loaded and saved to {csv_file_path}")
    return df

# Training model
def training_model(df, save_file="kmodes_model1.pkl", eval_metrics=False, init="Huang", n_init=15, random_state=42):
    # Preprocess data
    train_data = data_preprocess(df)
    
    silhou_scores = {}
    best_silhou_score = -1
    best_model = None

    # Training model with different K values
    for k in range(2, 10):
        km = KModes(n_clusters=k, init=init, n_init=n_init, random_state=random_state)
        
        # Fit the model and predict cluster assignments
        clusters = km.fit_predict(train_data)
        score = silhouette_score(train_data, clusters, metric='hamming')
        silhou_scores[k] = score
        
        # Update the best model if the current score is higher
        if score > best_silhou_score:
            best_silhou_score = score
            dv_score = davies_bouldin_score(train_data, clusters)
            best_model = km

    # Display evaluation metrics if enabled
    if eval_metrics:
        print(f"Best Silhouette Score (Hamming): {best_silhou_score}")
        print(f"Corresponding Davies-Bouldin Score: {dv_score}")
        
    # Save the best K-Modes model
    model_filename = save_file
    joblib.dump(best_model, model_filename)
    print(f"Model saved as {model_filename}")

    # Return the model
    return best_model

# Assign Cluster
def assign_cluster(model, df):
    # Preprocess the input data
    train_data = data_preprocess(df)
    pred = model.predict(train_data)

    # Add cluster assignments to the DataFrame
    df["Cluster"] = pred
    return df

# Recommend List
def recommend_cluster(model, data, df):
    """
    Args:
        model: Trained K-Modes model
        data: Single student's data (not preprocessed)
        df: DataFrame with cluster column included
    """
    # Preprocess the input data for prediction
    train_data = transfer_data(data)
    pred = model.predict(train_data)
    
    # Generate recommendationswd
    recommendations = []
    for idx, row in df[df["Cluster"] == pred[0]].iterrows():
        if str(row["FullName"]) != str(data["FullName"]):  # Exclude the input student
            recommendations.append({
                "FullName": row["FullName"],
                "Gender": row["Gender"],
                "Major": row["Major"],
                "Personality": row["Personality"],
                "Interests": row["Interests"],
                "Communication_Style": row["Communication_Style"],
                "Looking_for": row["Looking_for"],
                "Favorite_Subject": row["Favorite_Subject"],
                "Study_Location": row["Study_Location"],
                "Study_Time": row["Study_Time"]
            })

    return recommendations

@app.route("/")
def home():
    return "StudyBuddy Recommendation System for USTHConnect!"

@app.route("/test-db", methods=["GET"])
def test_db_connection():
    try:
        # Try to fetch data from the database
        df = load_data_from_db()
        # Return a preview of the data as a JSON response
        return jsonify({"status": "success", "data": df.head(5).to_dict(orient="records")})
    except Exception as e:
        # Catch and return any errors
        return jsonify({"status": "error", "message": str(e)})


@app.route("/train", methods=["GET"])
def train_model():
    # Set global variable
    global model
    global df

    # Load and preprocess the dataset
    # df = pd.read_csv(path)
    df = load_data_from_db()
    
    # Train the model
    model = training_model(df, save_file="kmodes_model2.pkl")

    # Assign clusters
    df = assign_cluster(model, df)

    return "Train complete"

@app.route("/train/sample/<string:name>/<string:gender>/<string:major>/<string:interest>/<string:communication>/<string:looking_for>/<string:fav_subject>/<string:location>/<string:time>/<string:personality>")
def load_sample(name,gender,major,interest,communication,looking_for,fav_subject,location,time,personality):
    # Set global variable
    global sample_data

    # Load data for prediction
    sample_data = pd.DataFrame({
        "FullName": [name],
        "Gender": [gender],
        "Major": [major],
        "Interests": [interest],
        "Communication_Style": [communication],
        "Looking_for": [looking_for],
        "Favorite_Subject": [fav_subject],
        "Study_Location": [location],
        "Study_Time": [time],
        "Personality": [personality]
    })

    return "Load Sample Complete!"

@app.route("/train/sample/<string:name>/<string:gender>/<string:major>/<string:interest>/<string:communication>/<string:looking_for>/<string:fav_subject>/<string:location>/<string:time>/<string:personality>/recommend")
def recommend_model(name,gender,major,interest,communication,looking_for,fav_subject,location,time,personality):
    # Return the results as a JSON response
    
    recommendations = recommend_cluster(model, sample_data, df)

    return jsonify(recommendations)


if __name__ == "__main__":
    app.run(debug=True)