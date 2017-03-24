package aykhadma.droidahmed.com.aykhaama.companies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.adapter.AirLinesAdapter;
import aykhadma.droidahmed.com.aykhaama.adapter.CompaniesAdapter;
import aykhadma.droidahmed.com.aykhaama.api.GetAirLine;
import aykhadma.droidahmed.com.aykhaama.api.GetCompanies;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnContactAction;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.model.AirLineMain;
import aykhadma.droidahmed.com.aykhaama.model.AirLineModel;
import aykhadma.droidahmed.com.aykhaama.model.CompaniesMain;
import aykhadma.droidahmed.com.aykhaama.model.CompaniesModel;
import aykhadma.droidahmed.com.aykhaama.model.CompaniesModel;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;

/**
 * Created by ahmed on 8/4/2016.
 */
public class Companies extends Fragment implements OnContactAction {
    View view;
    RecyclerView reCompany;
    CompaniesAdapter companiesAdapter;
    ArrayList<CompaniesModel> companiesModelArrayList;
    OnLoadingComplete onLoadingComplete;
    OnContactAction onContactAction;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.company_main,container,false);
        declare();

        return view;
    }
    private void declare() {
        companiesModelArrayList = new ArrayList<>();
        reCompany = (RecyclerView) view.findViewById(R.id.reCompnies);
        reCompany.setLayoutManager(new LinearLayoutManager(getActivity()));
        onContactAction = this;
        addData();
    }
    private void addData(){

        if (Utility.isNetworkConnected(getActivity())) {

            onLoadingComplete = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    CompaniesMain companiesMain = (CompaniesMain) result;
                    companiesModelArrayList = companiesMain.getData();

                    if (companiesModelArrayList.size() > 0) {
                        companiesAdapter = new CompaniesAdapter(companiesModelArrayList, getActivity(),onContactAction);
                        reCompany.setAdapter(companiesAdapter);
                    }

                }

                @Override
                public void onFailure() {
                    Utility.showFailureDialog(getActivity(), false);
                }
            };

            GetCompanies task = new GetCompanies(getActivity(), onLoadingComplete);
            task.execute();

        }

    }

    @Override
    public void onWebsiteClick(int pos) {
        Utility.openBrowser(getActivity(),companiesModelArrayList.get(pos).getWebsite());
    }

    @Override
    public void onPhoneClick(int pos) {
        Utility.confirmDialog(getActivity(),companiesModelArrayList.get(pos).getPhone());
    }

    @Override
    public void onEmailClick(int pos) {
         Utility.sendEmail(getActivity(),companiesModelArrayList.get(pos).getEmail());
    }
}
