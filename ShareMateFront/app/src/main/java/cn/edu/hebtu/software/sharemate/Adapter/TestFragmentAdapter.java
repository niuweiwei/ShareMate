package cn.edu.hebtu.software.sharemate.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import cn.edu.hebtu.software.sharemate.Fragment.FragmentTypeFirst;
import cn.edu.hebtu.software.sharemate.Fragment.FragmentTypeSecond;

/**
 * Created by janiszhang on 2016/6/10.
 */
//继承FragmentStatePagerAdapter
public class TestFragmentAdapter extends FragmentPagerAdapter {
    private FragmentManager mfragementManager;
    private List<Fragment> mlist;
    public static final String TAB_TAG = "@dream@";
    private List<String> mTitles;

    public TestFragmentAdapter(FragmentManager childFragmentManager, List<Fragment> mlist, List<String> mTitles) {
        super(childFragmentManager);
        this.mlist = mlist;
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        //初始化Fragment数据

        String[] title = mTitles.get(position).split(TAB_TAG);
        if(title[0].equals("推荐"))
        {
            FragmentTypeFirst fragment = new FragmentTypeFirst();
            fragment.setType(Integer.parseInt(title[1]));
            fragment.setTitle(title[0]);
            return fragment;
        }
        else {
            FragmentTypeSecond fragment=new FragmentTypeSecond();
            fragment.setType(Integer.parseInt(title[1]));
            fragment.setTitle(title[0]);
            return fragment;
        }




    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position).split(TAB_TAG)[0];
    }
}

