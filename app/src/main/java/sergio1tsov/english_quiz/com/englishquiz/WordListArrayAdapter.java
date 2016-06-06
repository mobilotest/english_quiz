package sergio1tsov.english_quiz.com.englishquiz;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sodintso on 6/5/16.
 */
public class WordListArrayAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList <String> mWords;

    public WordListArrayAdapter(Context context, ArrayList<String> words) {

        mContext = context;
        mWords = words;

    }

    @Override
    public int getGroupCount() {
        return mWords.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mWords.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String word = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.layout_word_list, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.txt_word_list);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(word);

        return convertView;


    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        //    final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.layout_child_word_list, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.txt_child);

        txtListChild.setText("placeholder");
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
