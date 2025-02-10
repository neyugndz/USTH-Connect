package vn.edu.usth.connect.Campus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.connect.Campus.RecyclerView.BuildingAdapter;
import vn.edu.usth.connect.Campus.RecyclerView.BuildingItem;
import vn.edu.usth.connect.R;

public class Building_Fragment extends Fragment {

    private List<BuildingItem> items;
    private BuildingAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fragment_building_.xml
        View v = inflater.inflate(R.layout.fragment_building_, container, false);

        // Setup Recyclerview for Building and SearchView
        setup_recyclerview_function(v);

        return v;
    }

    // SetUp RecyclerView and SearchView
    // Folder: RecyclerView: BuildingAdapter, BuildingItem, BuildingViewHolder
    private void setup_recyclerview_function(View v){
        // RecyclerView point to Detail.Detail_Building_Activity
        RecyclerView recyclerView = v.findViewById(R.id.building_recyclerview);

        items = new ArrayList<BuildingItem>();

        items.add(new BuildingItem("A21 - University of Science and Technology of Hanoi", "Building A21, No. 18 Hoang Quoc Viet, Nghia Do Ward, Cau Giay District, Hanoi", R.drawable.a21));
        items.add(new BuildingItem("VVL - Institute of Physics", "No, 10 Dao Tan, Cong Vi Ward, Ba Dinh District, Ha Noi", R.drawable.a13));
        items.add(new BuildingItem("A30 - Institute of Environmental Technology", "Building A30, No. 18 Hoang Quoc Viet, Nghia Do Ward, Cau Giay District, Hanoi", R.drawable.a30));
        items.add(new BuildingItem("Institute of Geology", "Lane 84, Chua Lang Ward, Lang Thuong, Dong Da, Hanoi, Vietnam", R.drawable.vvl));
        items.add(new BuildingItem("A3 - Institute of Information Technology", "Building A3, No. 18 Hoang Quoc Viet, Nghia Do Ward, Cau Giay District, Hanoi", R.drawable.a3));
        items.add(new BuildingItem("A10", "Building A10, No. 18 Hoang Quoc Viet, Nghia Do Ward, Cau Giay District, Hanoi", R.drawable.a28));
        items.add(new BuildingItem("2A", "Building 2A, No. 18 Hoang Quoc Viet, Nghia Do Ward, Cau Giay District, Hanoi", R.drawable.a28));
        items.add(new BuildingItem("2B - Center for High Technology Development", "Building 2B, No. 18 Hoang Quoc Viet, Nghia Do Ward, Cau Giay District, Hanoi", R.drawable.b2));
        items.add(new BuildingItem("2C", "Building 2C, No. 18 Hoang Quoc Viet, Nghia Do Ward, Cau Giay District, Hanoi", R.drawable.a28));
        items.add(new BuildingItem("2H - Institute of Physics", "Building 2H, No. 18 Hoang Quoc Viet, Nghia Do Ward, Cau Giay District, Hanoi", R.drawable.h2));
        items.add(new BuildingItem("A22 - Institute of Space Technology", "Building A22, No. 18 Hoang Quoc Viet, Nghia Do Ward, Cau Giay District, Hanoi", R.drawable.a22));
        items.add(new BuildingItem("A28 - Center for Research and Technology Transfer", "Building A28, No. 18 Hoang Quoc Viet, Nghia Do Ward, Cau Giay District, Hanoi", R.drawable.a28));

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new BuildingAdapter(requireContext(), items);
        recyclerView.setAdapter(adapter);

        // SearchView
        SearchView searchView = v.findViewById(R.id.building_searchView);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    // Filter for SearchView
    private void filterList(String text) {
        List<BuildingItem> filteredItems = new ArrayList<>();

        for (BuildingItem item : items) {
            if (item.getHeading().toLowerCase().contains(text.toLowerCase())) {
                filteredItems.add(item);
            }
        }

        if (filteredItems.isEmpty()) {
            Toast.makeText(requireContext(), "No results found", Toast.LENGTH_SHORT).show();
        }
        else{
            adapter.setFilter(filteredItems);
        }

    }
}