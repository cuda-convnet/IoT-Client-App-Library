package inc.osips.bleproject.view.fragments.control_fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import inc.osips.bleproject.R;
import inc.osips.bleproject.interfaces.ControlFragmentListener;
import inc.osips.bleproject.interfaces.ServiceSelectorListener;
import inc.osips.bleproject.view.activities.ControllerActivity;
import inc.osips.bleproject.view.listviews.DevicesViewHolderAdapter;

public class ServiceSelectorDialog extends DialogFragment implements ServiceSelectorListener {

    private static final String TAG = "ServiceSelectorDialog";
    ControlFragmentListener listener;
    private static List<String> listUUID;

    private RecyclerView listServices;
    private DevicesViewHolderAdapter adapter;
    private String selectedUUID;

    public static void setListUUID(List<String> listUUID) {
        ServiceSelectorDialog.listUUID = listUUID;
    }

    private ServiceSelectorDialog(){}

    public static ServiceSelectorDialog getInstance(){
        ServiceSelectorDialog dialog = new ServiceSelectorDialog();
        return dialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener =  (ControlFragmentListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.enter_uuid_ip_dialog, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialiseWidgets(view);
    }

    private void initialiseWidgets(View view){
        Button enterUUID = view.findViewById(R.id.buttonUUID);
        listServices = view.findViewById(R.id.listServiceUUID);
        adapter = new DevicesViewHolderAdapter(listUUID, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(listener.getFragmentactivity(),
                RecyclerView.VERTICAL, false);

        listServices.setLayoutManager(layoutManager);
        listServices.setAdapter(adapter);
        enterUUID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.setSelectedServiceUUID(selectedUUID);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        if(window == null) return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public void selectAServiceWith(int pos) {
        selectedUUID = listUUID.get(pos);
        Log.d(TAG, "selectAServiceWith: " + selectedUUID);
        adapter.notifyDataSetChanged();
    }

    @Override
    public Context getListenerContext() {
        return listener.getFragmentactivity();
    }
}
