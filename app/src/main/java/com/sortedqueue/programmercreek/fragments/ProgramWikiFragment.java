package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.ProgramWikiRecyclerAdapter;
import com.sortedqueue.programmercreek.database.ProgramWiki;
import com.sortedqueue.programmercreek.database.WikiModel;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2016-12-31.
 */

public class ProgramWikiFragment extends Fragment {

    @Bind(R.id.headerTextView)
    TextView headerTextView;
    @Bind(R.id.programWikiRecyclerView)
    RecyclerView programWikiRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_program_wiki, null);
        ButterKnife.bind(this, fragmentView);

        ArrayList<ProgramWiki> programWikis = new ArrayList<>();
        ProgramWiki programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_HEADER);
        programWiki.setHeader("Hello World");
        programWikis.add(programWiki);

        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_PROGRAM_EXPLANATION);
        programWiki.setProgramExplanation("" +
                "How \"Hello, World!\" program works?\n" +
                "\n" +
                "The #include <stdio.h> is a preprocessor command. This command tells compiler to include the contents of stdio.h (standard input and output) file in the program.\n\n" +
                "The stdio.h file contains functions such as scanf() and print() to take input and display output respectively.\n\n" +
                "If you use printf() function without writing #include <stdio.h>, the program will not be compiled.\n\n" +
                "The execution of a C program starts from the main() function.\n\n" +
                "The printf() is a library function to send formatted output to the screen. In this program, the printf() displays Hello, World! text on the screen.\n\n" +
                "The return 0; statement is the \"Exit status\" of the program. In simple terms, program ends with this statement.\n\n");
        programWikis.add(programWiki);

        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_PROGRAM);
        programWiki.setProgramExample(
                "#include <stdio.h>\n" +
                "int main()\n" +
                "{\n" +
                "   // printf() displays the string inside quotation\n" +
                "   printf(\"Hello, World!\");\n" +
                "   return 0;\n" +
                "}");
        programWiki.setOutput("" +
                "Output\n" +
                "\n" +
                "Hello, World!");
        programWikis.add(programWiki);



        WikiModel wikiModel = new WikiModel("c1", "Hello World", programWikis );

        headerTextView.setText(wikiModel.getWikiHeader());

        setupRecyclerView( wikiModel );

        return fragmentView;
    }

    private void setupRecyclerView(WikiModel wikiModel) {
        programWikiRecyclerView.setAdapter( new ProgramWikiRecyclerAdapter( getContext(), wikiModel.getProgramWikis() ));
        programWikiRecyclerView.setLayoutManager( new LinearLayoutManager(getContext()) );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
