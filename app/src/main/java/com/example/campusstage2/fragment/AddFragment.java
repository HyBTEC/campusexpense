package com.example.campusstage2.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campusstage2.Adapter.CategoryAdapter;
import com.example.campusstage2.Auth;
import com.example.campusstage2.Expense;
import com.example.campusstage2.MainActivity;
import com.example.campusstage2.R;
import com.example.campusstage2.model.Category;

import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public EditText editTextDate;
    public TextView selectCategory;
    Category selectedCategory;
    Auth auth;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        auth = new Auth(getContext());
        editTextDate = view.findViewById(R.id.date);
        editTextDate.setOnTouchListener((view1, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                showDatePickerDialog();
            }
            return true;
        });
        selectCategory = view.findViewById(R.id.chooseCategory);
        selectCategory.setOnClickListener(view1 -> {
            showCategoryDialog();
        });
        Button saveExpense = view.findViewById(R.id.button);
        saveExpense.setOnClickListener(view1 -> {
            EditText amount = view.findViewById(R.id.amountInput);
            EditText note = view.findViewById(R.id.noteInput);
            Expense expense = new Expense(this.getContext());
            expense.insertExpense(
                    Integer.valueOf(amount.getText().toString()),
                    selectedCategory.getId(),
                    auth.getUserId(),
                    editTextDate.getText().toString(),
                    note.getText().toString()
            );
            Toast.makeText(this.getContext(),"Created expense",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this.getContext(), MainActivity.class);
            this.getContext().startActivity(intent);
        });
        selectCategory = view.findViewById(R.id.chooseCategory);

        return view;
    }


    private void showCategoryDialog() {
        Category category = new Category(getContext());
        List<Category> categories = category.getAllCategories();
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.category_list_view, null);
        ListView categoryListView = dialogView.findViewById(R.id.categoryList);

        CategoryAdapter adapter = new CategoryAdapter(getContext(), categories);
        categoryListView.setAdapter(adapter);

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Select Category")
                .setView(dialogView)
                .create();
        categoryListView.setOnItemClickListener((parent, view, position, id) -> {
            selectedCategory = categories.get(position);
            selectCategory.setText(selectedCategory.toString());
            dialog.dismiss();
        });
        dialog.show();
        Button addCategory = dialogView.findViewById(R.id.addCategory);
        addCategory.setOnClickListener(view -> {
            dialog.dismiss();
            View createCategoryView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_category, null);

            AlertDialog createCategoryDialog = new AlertDialog.Builder(getContext())
                    .setTitle("Add Category")
                    .setView(createCategoryView)
                    .create();
            createCategoryDialog.show();
            Button saveCategory = createCategoryDialog.findViewById(R.id.submitCategory);
            saveCategory.setOnClickListener(view1 -> {
                EditText newCategoryInput = createCategoryDialog.findViewById(R.id.categoryInput);
                Category newCategory = new Category(this.getActivity().getBaseContext());
                newCategory.insertCategory(newCategoryInput.getText().toString(), null, auth.getUserId());
                createCategoryDialog.dismiss();
                showCategoryDialog();
            });
        });
    }

    public void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    editTextDate.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                }, year, month, day);
        datePickerDialog.show();
    }
}
