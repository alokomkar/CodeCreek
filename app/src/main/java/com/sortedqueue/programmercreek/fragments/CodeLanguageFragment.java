package com.sortedqueue.programmercreek.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.constants.LanguageConstants;
import com.sortedqueue.programmercreek.database.firebase.Code;
import com.sortedqueue.programmercreek.interfaces.CodeLabNavigationListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok on 12/04/17.
 */

public class CodeLanguageFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.cProgramsTextView)
    TextView cProgramsTextView;
    @Bind(R.id.cppProgramsTextView)
    TextView cppProgramsTextView;
    @Bind(R.id.javaProgramsTextView)
    TextView javaProgramsTextView;
    @Bind(R.id.adaProgramsTextView)
    TextView adaProgramsTextView;

    private CodeLabNavigationListener codeLabNavigationListener;

    private static CodeLanguageFragment instance;
    private Code code;

    public static CodeLanguageFragment getInstance() {
        if (instance == null) {
            instance = new CodeLanguageFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_code_language_list, container, false);
        ButterKnife.bind(this, view);
        code = new Code();
        code.setLanguage((LanguageConstants.C_INDEX));
        code.setSourceCode("#include<stdio.h>\n" +
                "#include<math.h>\n" +
                "int main(void)\n" +
                "{\n" +
                "   double Adjacent=2, Opposite=3, Hypotenuse=4;\n" +
                "   //Hypotenuse\n" +
                "   double Hypotenuse1 = (pow(Adjacent,2)) + (pow(Opposite,2));\n" +
                "   Hypotenuse1=sqrt(Hypotenuse1);\n" +
                "   printf(\"\\nHypotenuse: %lf\",Hypotenuse1);\n" +
                "   //Adjacent\n" +
                "   double Adjacent1 = (pow(Hypotenuse,2)) - (pow(Opposite,2)) ;\n" +
                "   Adjacent1=sqrt(Adjacent1);\n" +
                "   printf(\"\\nAdjacent: %lf\",Adjacent1);\n" +
                "     \n" +
                "   //Opposite\n" +
                "   double Opposite1 = (pow(Hypotenuse,2)) - (pow(Adjacent,2));\n" +
                "   Opposite1=sqrt(Opposite1);\n" +
                "   printf(\"\\nOpposite: %lf\",Opposite1);\n" +
                "   return 0;\n" +
                "}\n");
        setupListeners();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if( context instanceof  CodeLabNavigationListener ) {
            codeLabNavigationListener = (CodeLabNavigationListener) context;
        }
    }

    private void setupListeners() {
        cProgramsTextView.setOnClickListener(this);
        cppProgramsTextView.setOnClickListener(this);
        javaProgramsTextView.setOnClickListener(this);
        adaProgramsTextView.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.cProgramsTextView :
                codeLabNavigationListener.loadCompileCodeFragment(code);
                break;
            case R.id.cppProgramsTextView :
                codeLabNavigationListener.loadCompileCodeFragment(code);
                break;
            case R.id.javaProgramsTextView :
                codeLabNavigationListener.loadCompileCodeFragment(code);
                break;
            case R.id.adaProgramsTextView:
                codeLabNavigationListener.loadCompileCodeFragment(code);
                break;
        }
    }
}
