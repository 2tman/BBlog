package iandroid.club.bhome_module.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.iandroid.bbase_module.router.HomeRouteUtils;

import iandroid.club.bhome_module.R;


/**
 * A simple {@link Fragment} subclass.
 */
@Route(path = HomeRouteUtils.Home_Fragment_Main)
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

}
