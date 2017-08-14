package justjava.android.example.packagecom.Expandabale.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import justjava.android.example.packagecom.Expandabale.Adapters.ExpandListAdapter;
import justjava.android.example.packagecom.Expandabale.Pojo.Child;
import justjava.android.example.packagecom.Expandabale.Pojo.GroupModel;
import justjava.android.example.packagecom.Expandabale.R;
import justjava.android.example.packagecom.Expandabale.Utils.Util;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private ExpandListAdapter ExpAdapter;
    private  ProgressDialog  dialog;
    private Util util;
    private ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        util=new Util(getApplicationContext());
        expandableListView= (ExpandableListView) findViewById(R.id.expandablelist);

        try {
            bikeFullServiceapi();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void bikeFullServiceapi() {

        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();


            util.getBaseClassService(getApplicationContext(), "").bikeFullServiceapi(new Callback<JsonObject>() {
                @Override
                public void success(JsonObject jsonObject, Response response) {

                    ArrayList<GroupModel> groupArrayList = new ArrayList<>();

                    JsonObject jsonObject1 = jsonObject.getAsJsonObject();
                    JsonArray jsonArray = jsonObject1.get("data").getAsJsonArray();
                    for (int i = 0; i < jsonArray.size(); i++) {

                        GroupModel groupModel = new GroupModel();

                        JsonObject jsonObject2 = jsonArray.get(i).getAsJsonObject();
                        int bikeServiceId = jsonObject2.get("id").getAsInt();
                        String serviceCatName = jsonObject2.get("service_category_name").getAsString();
                        JsonArray jsonArray1 = jsonObject2.get("Bikeservicesubcategory").getAsJsonArray();

                        ArrayList<Child> childArrayList = new ArrayList<>();

                        for (int i1 = 0; i1 < jsonArray1.size(); i1++) {


                            JsonObject jsonObject3 = jsonArray1.get(i1).getAsJsonObject();
                            String subCategory = jsonObject3.get("service_subcategory_name").getAsString();
                            int subcategoryId = jsonObject3.get("service_category_id").getAsInt();
                            int bVid = jsonObject3.get("bike_variant_id").getAsInt();
                            String subCatPrice = jsonObject3.get("service_price").getAsString();

                            Child child = new Child();
                            child.setName(subCategory);
                            child.setPrice(subCatPrice);
                            child.setSelected(true);
                            childArrayList.add(child);


                        }

                        groupModel.setName(serviceCatName);
                        groupModel.setItems(childArrayList);

                        groupArrayList.add(groupModel);

                        dialog.dismiss();


                    }

                    ExpAdapter = new ExpandListAdapter(MainActivity.this, groupArrayList);
                    expandableListView.setAdapter(ExpAdapter);
                }

                @Override
                public void failure(RetrofitError error) {
                    dialog.dismiss();
                    util.showToast(getApplicationContext(), "" + error.getMessage());
                }
            });


        }


    }

