package am.newway.lesson4.fragments;

import am.newway.lesson4.R;
import am.newway.lesson4.adapter.BasicRecyclerAdapter;
import am.newway.lesson4.data.RecyclerViewMargin;
import am.newway.lesson4.data.Settings;
import am.newway.lesson4.data.Tasks;
import am.newway.lesson4.enums.TaskType;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SecondFragment extends BaseFragment {
    private BasicRecyclerAdapter mAdapter;

    public SecondFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = view.findViewById(R.id.text);

        RecyclerView recyclerView = view.findViewById(R.id.recycler);

        if (!Settings.getInstance(Objects.requireNonNull(getActivity())).isReadContactsAccess()) {
            textView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            return;
        }

        RecyclerView.LayoutManager mManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(mManager);

        mAdapter = new BasicRecyclerAdapter(new ArrayList<Tasks>());
        recyclerView.setAdapter(mAdapter);

        RecyclerViewMargin decoration = new RecyclerViewMargin((int) getResources().getDimension(R.dimen.recycler_item_margin));
        recyclerView.addItemDecoration(decoration);

        if (getActivity() != null) {
            List<Tasks> tasks = readContacts();
            for (Tasks task : tasks) {
                mAdapter.addTasks(task);
            }
        }
    }

    @Override
    public void setTask(Tasks task) {
        mAdapter.addTasks(task);
    }

    private List<Tasks> readContacts() {
        List<Tasks> contacts = new ArrayList<>();

        Uri contactURI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        final String ID = ContactsContract.Contacts._ID;
        final String displayName = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;

        final String[] projection = new String[]{ID, ContactsContract.CommonDataKinds.Phone.NUMBER, displayName};

        String orderDisplayName = ContactsContract.Contacts.DISPLAY_NAME + " ASC ";

        String selection = ContactsContract.CommonDataKinds.Phone.TYPE
                + " = "
                + ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;


        Cursor contactCursor = getActivity().getContentResolver().query(contactURI,
                projection,
                selection,
                null,
                orderDisplayName);


        if (contactCursor == null) {
            return contacts;
        }

        final int displayNameIndex = contactCursor.getColumnIndex(displayName);
        final int phoneNumberIndex = contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);

        contactCursor.moveToFirst();

        do {
            String name = contactCursor.getString(displayNameIndex);
            String phone = contactCursor.getString(phoneNumberIndex);

            contacts.add(new Tasks(name, phone, Uri.parse(""), TaskType.UNI.toString()));

        } while (contactCursor.moveToNext());

        contactCursor.close();

        return contacts;
    }
}
