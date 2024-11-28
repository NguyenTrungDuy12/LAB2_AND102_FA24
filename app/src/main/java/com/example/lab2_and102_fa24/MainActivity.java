package com.example.lab2_and102_fa24;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2_and102_fa24.adapter.ProductAdapter;
import com.example.lab2_and102_fa24.model.Product;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edtId;
    private EditText edtName;
    private EditText edtMota;
    private EditText edtGia;
    private Button btnAdd,btnUpdate,btnDelete;

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        productList = new ArrayList<>();
        adapter = new ProductAdapter(productList);
        recyclerView.setAdapter(adapter);

        // Lấy danh sách sản phẩm từ Firestore
        fetchProducts();

        btnAdd.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String description = edtMota.getText().toString().trim();
            String priceStr = edtGia.getText().toString().trim();

            if (!name.isEmpty() && !description.isEmpty() && !priceStr.isEmpty()) {
                double price = Double.parseDouble(priceStr);
                Product product = new Product(null, name, description, price);
                addProduct(product);
            } else {
                Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate.setOnClickListener(v -> {
            String id = edtId.getText().toString().trim();
            String name = edtName.getText().toString().trim();
            String description = edtMota.getText().toString().trim();
            String priceStr = edtGia.getText().toString().trim();

            if (!id.isEmpty() && !name.isEmpty() && !description.isEmpty() && !priceStr.isEmpty()) {
                double price = Double.parseDouble(priceStr);
                Product updatedProduct = new Product(id, name, description, price);
                updateProduct(id, updatedProduct);
            } else {
                Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(v -> {
            String id = edtId.getText().toString().trim();
            if (!id.isEmpty()) {
                deleteProduct(id);
            } else {
                Toast.makeText(MainActivity.this, "Vui lòng nhập ID sản phẩm cần xóa!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void fetchProducts() {
        db.collection("products")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    productList.clear(); // Xóa danh sách cũ
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        Product product = document.toObject(Product.class);
                        product.setId(document.getId()); // Gán ID từ Firestore
                        productList.add(product);
                    }
                    adapter.notifyDataSetChanged(); // Cập nhật RecyclerView
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Error fetching products", e));
    }


    public void addProduct(Product product) {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("name", product.getName());
        productMap.put("description", product.getDescription());
        productMap.put("price", product.getPrice());

        db.collection("products")
                .add(productMap)
                .addOnSuccessListener(documentReference -> {
                    String generatedId = documentReference.getId();
                    product.setId(generatedId);
                    productMap.put("id", generatedId); // Lưu `id` vào tài liệu Firestore
                    db.collection("products").document(generatedId).update("id", generatedId);

                    productList.add(product);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                    clearInputFields();
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error adding product", e);
                    Toast.makeText(MainActivity.this, "Thêm sản phẩm thất bại!", Toast.LENGTH_SHORT).show();
                });

    }


    public void updateProduct(String productId, Product updatedProduct) {
        HashMap<String, Object> updatedData = new HashMap<>();
        updatedData.put("name", updatedProduct.getName());
        updatedData.put("description", updatedProduct.getDescription());
        updatedData.put("price", updatedProduct.getPrice());

        db.collection("products").document(productId)
                .update(updatedData)
                .addOnSuccessListener(aVoid -> {
                    // Cập nhật sản phẩm trong danh sách
                    for (Product product : productList) {
                        if (product.getId().equals(productId)) {
                            product.setName(updatedProduct.getName());
                            product.setDescription(updatedProduct.getDescription());
                            product.setPrice(updatedProduct.getPrice());
                            break;
                        }
                    }
                    adapter.notifyDataSetChanged(); // Cập nhật RecyclerView
                    Toast.makeText(MainActivity.this, "Cập nhật sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                    clearInputFields();
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error updating product", e);
                    Toast.makeText(MainActivity.this, "Cập nhật sản phẩm thất bại!", Toast.LENGTH_SHORT).show();
                });
    }


    public void deleteProduct(String productId) {
        db.collection("products").document(productId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // Xóa sản phẩm khỏi danh sách
                    for (int i = 0; i < productList.size(); i++) {
                        if (productList.get(i).getId().equals(productId)) {
                            productList.remove(i);
                            break;
                        }
                    }
                    adapter.notifyDataSetChanged(); // Cập nhật RecyclerView
                    Toast.makeText(MainActivity.this, "Xóa sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                    clearInputFields();
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error deleting product", e);
                    Toast.makeText(MainActivity.this, "Xóa sản phẩm thất bại!", Toast.LENGTH_SHORT).show();
                });
    }

    public void init(){
        edtId = findViewById(R.id.edtid);
        edtName = findViewById(R.id.edtName);
        edtMota = findViewById(R.id.edtMota);
        edtGia = findViewById(R.id.edtGia);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        recyclerView = findViewById(R.id.lvToDoList);
    }

    private void clearInputFields() {
        edtId.setText("");
        edtName.setText("");
        edtMota.setText("");
        edtGia.setText("");
    }
}