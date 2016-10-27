package work.technie.motonavigator.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import work.technie.motonavigator.R;

/**
 * Created by anupam on 27/10/16.
 */

public class AboutFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.about, container, false);

        TextView txtView = (TextView) rootView.findViewById(R.id.about_text);
        txtView.setMovementMethod(LinkMovementMethod.getInstance());

        return rootView;
    }
}
