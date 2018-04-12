package soul.listener.com.humiture.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;

import butterknife.BindView;
import soul.listener.com.humiture.R;
import soul.listener.com.humiture.a_model.ResidentModel;
import soul.listener.com.humiture.a_presenters.HumiturePresenter;
import soul.listener.com.humiture.a_views.HumitureView;
import soul.listener.com.humiture.base.BaseMvpActivity;

/**
 * Created by kys_31 on 2017/12/5.
 */

public class HumitureActivity extends BaseMvpActivity<HumiturePresenter> implements HumitureView {
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.iv_filter)
    ImageView ivFilter;
    @BindView(R.id.lv_lineChat)
    public LineChart mDoubleLineChar;
    @BindView(R.id.bt_upPage)
    Button btUpPage;
    @BindView(R.id.bt_nextPage)
    Button btNextPage;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    private boolean mBrClick = true;
    private ResidentModel mResidentModel;

    @Override
    public HumiturePresenter createPresenter() {
        return new HumiturePresenter(this);
    }

    @Override
    protected int getLayouID() {
        return R.layout.layout_fullscreen;
    }

    @Override
    protected void initView() {
         /*设置状态栏颜色*/
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.text_black));

    }

    @Override
    protected void initData(){
        mPresenter.initBlockData();
    }

    /**
     * 判断是否是用户通过手机号登录的
     */
    @Override
    public void userLogin(){
        Intent intent = getIntent();
        mResidentModel = (ResidentModel) intent.getSerializableExtra("userMessage");
        if (mResidentModel != null){
            mBrClick = false;
            mPresenter.showOnlyUser(mResidentModel);
            return;
        }
        mPresenter.showFilterDialog(mBrClick);
    }

    @Override
    public void setAddress(String str) {
        tvAddress.setText(str);
    }

    @Override
    public void setNextClick(boolean click) {
        btNextPage.setEnabled(click);
        if (!click){
            btNextPage.setText("尾页");
            btNextPage.setTextColor(Color.GRAY);
        }else {
            btNextPage.setText("下一页");
            btNextPage.setTextColor(Color.WHITE);
        }
    }

    @Override
    public void setUpClick(boolean click) {
        btUpPage.setEnabled(click);
        if (!click){
            btUpPage.setText("首页");
            btUpPage.setTextColor(Color.GRAY);
        }else {
            btUpPage.setText("上一页");
            btUpPage.setTextColor(Color.WHITE);
        }
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                onBackPressed();
                break;
            case R.id.iv_filter:
                if (mBrClick){
                    mPresenter.showFilterDialog( mBrClick);
                }else {
                    mPresenter.showOnlyUser(mResidentModel);
                }
                break;
            case R.id.bt_nextPage:
                mPresenter.nextPage();
                break;
            case R.id.bt_upPage:
                mPresenter.upPage();
                break;
            default:break;
        }
    }

}
