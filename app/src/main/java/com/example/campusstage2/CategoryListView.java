package com.example.campusstage2;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.example.campusstage2.Adapter.CategoryAdapter;
import com.example.campusstage2.model.Category;

import java.util.List;

public class CategoryListView {
    private View view;
    private ListView categoryListView;
    private CategorySelectionListener selectionListener; // Listener for category selection
    AlertDialog dialog;
    // Constructor to initialize the ListView and its layout
    public CategoryListView(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.category_list_view, null);
        categoryListView = view.findViewById(R.id.categoryList);
    }

    public View getView() {
        return view;
    }

    // Load categories into the ListView
    public void loadCategories(Context context,List<Category> categories) {
        CategoryAdapter adapter = new CategoryAdapter(context,categories);
        categoryListView.setAdapter(adapter);

        // Set item click listener to handle category selection
        categoryListView.setOnItemClickListener((parent, view, position, id) -> {
            Category selectedCategory = categories.get(position);
            if (selectionListener != null) {
                selectionListener.onCategorySelected(selectedCategory); // Notify listener
            }
            dialog.dismiss();

        });
    }

    // Method to show the category selection dialog
    public void showCategoryDialog(Context context, List<Category> categories) {
        loadCategories(context, categories); // Load categories into ListView

        // Show the dialog with the ListView
         dialog = new AlertDialog.Builder(context)
                .setTitle("Select Category")
                .setView(view)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.show();
    }

    // Setter for the selection listener
    public void setCategorySelectionListener(CategorySelectionListener listener) {
        this.selectionListener = listener;
    }

    // Interface for communicating selected category
    public interface CategorySelectionListener {
        void onCategorySelected(Category category);
    }
}
