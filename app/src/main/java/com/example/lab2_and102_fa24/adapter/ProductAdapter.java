package com.example.lab2_and102_fa24.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.lab2_and102_fa24.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2_and102_fa24.model.Product;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.name.setText(product.getName());
        holder.description.setText(product.getDescription());
        holder.price.setText(String.valueOf(product.getPrice()));

        // Add Edit and Delete functionality
        holder.editButton.setOnClickListener(v -> editProduct(product, position, holder));
        holder.deleteButton.setOnClickListener(v -> deleteProduct(product, position, holder));
    }

    @Override
    public int getItemCount() {
        if(productList != null) {
         return productList.size();
        }else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, price;
        Button editButton, deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            description = itemView.findViewById(R.id.product_description);
            price = itemView.findViewById(R.id.product_price);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }

    private void editProduct(Product product, int position, ViewHolder holder) {
        // For now, let's just show a Toast with the product name
        // You could replace this with a dialog or another activity for updating product details
        Toast.makeText(holder.itemView.getContext(), "Editing product: " + product.getName(), Toast.LENGTH_SHORT).show();

        // Assuming the product details are updated (you can implement a dialog to get the new details)
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("products").document(product.getId())
                .set(product)  // Update with new product details
                .addOnSuccessListener(aVoid -> {
                    // Optionally, update the product in the list and notify the adapter
                    productList.set(position, product);
                    notifyItemChanged(position);
                    Toast.makeText(holder.itemView.getContext(), "Product updated!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(holder.itemView.getContext(), "Error updating product", Toast.LENGTH_SHORT).show();
                });
    }

    private void deleteProduct(Product product, int position, ViewHolder holder) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("products").document(product.getId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // Remove the product from the list and notify the adapter
                    productList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(holder.itemView.getContext(), "Product deleted", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(holder.itemView.getContext(), "Error deleting product", Toast.LENGTH_SHORT).show();
                });
    }
}
