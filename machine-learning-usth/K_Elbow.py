from yellowbrick.cluster import KElbowVisualizer
import matplotlib.pyplot as plt

def elbow_method(data, model, visualize=False):

    if visualize:
        visualizer = KElbowVisualizer(model, k=(2, 10))
        visualizer.fit(data)
        best_k = visualizer.elbow_value_
        visualizer.show()
        plt.show()
    else:
        plt.ioff()   
        visualizer = KElbowVisualizer(model, k=(2, 10))
        visualizer.fit(data)
        best_k = visualizer.elbow_value_         
        plt.close()
        
    return best_k