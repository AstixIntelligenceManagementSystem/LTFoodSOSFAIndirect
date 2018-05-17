package project.astix.com.ltfoodsosfaindirect;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class DistributorProfilingActivity extends AppCompatActivity {

    LinkedHashMap<String,String> hashMapDist=new LinkedHashMap<>();
    LinearLayout ll_distributor;
    View previousDistSlctd;
    boolean isSelectedStore=false;
    DBAdapterKenya dbEngine=new DBAdapterKenya(this);
    Button btn_next,BackBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_profiling);
        ll_distributor= (LinearLayout) findViewById(R.id.ll_distributor);
        btn_next= (Button) findViewById(R.id.btn_next);
        BackBtn= (Button) findViewById(R.id.BackBtn);
        addDistributor();
        if(hashMapDist!=null && (!hashMapDist.isEmpty())){
            inflateDistributor();
        }


        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DistributorProfilingActivity.this,AllButtonActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void nextButtonFunctionality() {
        String NodeID_NodeType=previousDistSlctd.getTag().toString();

        Intent intent=new Intent(DistributorProfilingActivity.this,RegistrationActivity.class);
        intent.putExtra("NodeID_NodeType",NodeID_NodeType);
        startActivity(intent);
        finish();

    }

    private void inflateDistributor() {

        LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for(Map.Entry<String,String> entry:hashMapDist.entrySet())
        {
            String distName=entry.getValue().split(Pattern.quote("^"))[0];
            String flgRemap=entry.getValue().split(Pattern.quote("^"))[1];
            String lastUpdateDate=entry.getValue().split(Pattern.quote("^"))[2];
            String nodeId_nodeType=entry.getKey();
            View viewDistributor=inflater.inflate(R.layout.list_distributor,null);
         final    LinearLayout layoutOfRow=(LinearLayout) viewDistributor.findViewById(R.id.layoutOfRow);

            TextView lastWorkingdate= (TextView) viewDistributor.findViewById(R.id.lastWorkingdate);
            if((!lastUpdateDate.equals("")) && (!lastUpdateDate.equals("0"))){
                lastWorkingdate.setText(lastUpdateDate);
            }

            TextView txtDstributor= (TextView) viewDistributor.findViewById(R.id.txtDstributor);
            txtDstributor.setText(distName);
            txtDstributor.setTag(nodeId_nodeType);
            ll_distributor.addView(viewDistributor);
            if(flgRemap.equals("0"))
            {
                txtDstributor.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.grey_dot,0);
                txtDstributor.setTextColor(getResources().getColor(R.color.colorGray));
                txtDstributor.setOnClickListener(null);
            }
            else
            {
                txtDstributor.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.green_dot,0);
                txtDstributor.setTextColor(getResources().getColor(R.color.colorGreen));

                txtDstributor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        layoutOfRow.setBackgroundResource(R.drawable.card_bckg_slctd)	;
                      String NodeID_NodeType=  v.getTag().toString();

                        Intent intent=new Intent(DistributorProfilingActivity.this,RegistrationActivity.class);
                        intent.putExtra("NodeID_NodeType",NodeID_NodeType);
                        startActivity(intent);
                        finish();


                    }
                });
            }



        }


    }

    private void addDistributor() {
        hashMapDist=dbEngine.fnGettblDSRList();



    }


}
