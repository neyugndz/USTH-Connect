package vn.edu.usth.connect.Resource.ResourceRecyclerView;

import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.connect.Models.Moodle.Resource;
import vn.edu.usth.connect.R;

public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.ResourceViewHolder> {


    private List<Resource> resourceList;

    public ResourceAdapter(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }

    public void setResources(List<Resource> resources) {
        this.resourceList = resources;
        notifyDataSetChanged();
    }

    @Override
    public ResourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_resource, parent, false);
        return new ResourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResourceViewHolder holder, int position) {
        Resource resource = resourceList.get(position);
        Log.d("ResourceAdapter", "Binding Resource: " + resource.getResourceName() + ", URL: " + resource.getResourceUrl());

        holder.resourceName.setText(resource.getResourceName());
        holder.resourceUrl.setText(resource.getResourceUrl());
        holder.resourceIcon.setImageResource(R.drawable.file);

        // Set an OnClickListener to handle item click
        holder.itemView.setOnClickListener(v -> {
            String url = resource.getResourceUrl();
            if (url != null && !url.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                v.getContext().startActivity(intent);
            } else {
                Toast.makeText(v.getContext(), "Invalid URL", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return resourceList.size();
    }

    public static class ResourceViewHolder extends RecyclerView.ViewHolder {
        TextView resourceName, resourceUrl;
        ImageView resourceIcon;

        public ResourceViewHolder(View itemView) {
            super(itemView);
            resourceName = itemView.findViewById(R.id.resource_name);
            resourceUrl = itemView.findViewById(R.id.resource_url);
            resourceIcon = itemView.findViewById(R.id.resource_icon);
        }
    }
}

