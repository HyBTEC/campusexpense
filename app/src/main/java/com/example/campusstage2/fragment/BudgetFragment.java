package com.example.campusstage2.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.campusstage2.Adapter.BudgetAdapter;
import com.example.campusstage2.AddBudgetActivity;
import com.example.campusstage2.DatabaseHelper;
import com.example.campusstage2.R;
import com.example.campusstage2.model.Budget;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BudgetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BudgetFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView budgetRecyclerView;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BudgetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BudgetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BudgetFragment newInstance(String param1, String param2) {
        BudgetFragment fragment = new BudgetFragment();
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
        View view = inflater.inflate(R.layout.fragment_budget, container, false);
        Button addBudgetButton = view.findViewById(R.id.addBudgetButton);
        addBudgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddBudgetActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        budgetRecyclerView = view.findViewById(R.id.expenseRecyclerView);
        budgetRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Budget> budgets = loadBudgets();
        BudgetAdapter adapter = new BudgetAdapter(budgets);
        budgetRecyclerView.setAdapter(adapter);
        return view;
    }

    private List<Budget> loadBudgets() {
        List<Budget> budgets = new ArrayList<>();
        SQLiteDatabase db = new DatabaseHelper(getContext()).getReadableDatabase();
        String query = "SELECT b.id, b.amount, b.remaining, " +
                "b.category_id, b.user_id, b.start_date, b.end_date, c.name AS category_name " +
                "FROM budgets b " +
                "LEFT JOIN categories c ON b.category_id = c.id";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Integer id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                int amount = cursor.getInt(cursor.getColumnIndexOrThrow("amount"));
                int remaining = cursor.getInt(cursor.getColumnIndexOrThrow("remaining"));
                Integer categoryId = cursor.getInt(cursor.getColumnIndexOrThrow("category_id"));
                Integer userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                String startDate = cursor.getString(cursor.getColumnIndexOrThrow("start_date"));
                String endDate = cursor.getString(cursor.getColumnIndexOrThrow("end_date"));
                String categoryName = cursor.getString(cursor.getColumnIndexOrThrow("category_name"));

                Budget budget = new Budget(id, amount, categoryId, userId, startDate, endDate);
                budget.setCategoryName(categoryName);
                budget.setRemaining(remaining);                budgets.add(budget);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return budgets;
    }

}