package ui;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import com.andlei.baseapp.R;
import com.tencent.bugly.beta.Beta;

import base.activity.BaseFragmentActivity;
import netWork.NetUtils;
import ui.fragment.HomePageFragment;
import ui.fragment.MineFragment;
import ui.fragment.StoreFragment;
import ui.fragment.WorkSpaceFragment;
import utils.Logger;

/**
 * @author Andlei
 * @date 2019/6/24.
 */
public class MainActivity extends BaseFragmentActivity {
    private RadioGroup radioGroup;
    private NetUtils netUtils;
    private HomePageFragment homepageFragment;
    private StoreFragment storeFragment;
    private WorkSpaceFragment workSpaceFragment;
    private MineFragment mineFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViews() {
        radioGroup = getView(R.id.rd_group);
        homepageFragment = HomePageFragment.getInstance();
        storeFragment = StoreFragment.getInstance();
        workSpaceFragment = WorkSpaceFragment.getInstance();
        mineFragment = MineFragment.getInstance();
        addFragment(R.id.FrameLayout, homepageFragment);
        addFragment(R.id.FrameLayout,storeFragment);
        addFragment(R.id.FrameLayout,workSpaceFragment);
        addFragment(R.id.FrameLayout, mineFragment);
        hideAllFragment();
        showFragment(homepageFragment);
    }

    @Override
    protected void formatViews() {
        radioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            hideAllFragment();
            switch (i) {
                case R.id.rd_menu_homepage:
                    if (homepageFragment == null) {
                        homepageFragment = HomePageFragment.getInstance();
                    }
                    showFragment(homepageFragment);
                    break;
                case R.id.rd_menu_store:
                    if (storeFragment == null) {
                        storeFragment = StoreFragment.getInstance();
                    }
                    showFragment(storeFragment);
                    break;
                case R.id.rd_menu_worksapce:
                    if (workSpaceFragment == null) {
                        workSpaceFragment = WorkSpaceFragment.getInstance();
                    }
                    showFragment(workSpaceFragment);
                    break;
                case R.id.rd_menu_user:
                    if (mineFragment == null) {
                        mineFragment = MineFragment.getInstance();
                    }
                    showFragment(mineFragment);
                    break;
            }
        }
    };

    @Override
    protected void formatData() {
        Beta.checkUpgrade(false,false);
//        CrashReport.testJavaCrash();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","2019072665936838","MIIEoAIBAAKCAQEA7Rew5YKutfPUjDa2BsoDng/8eG95Uuc8+gaz5Q//2jwjV8T9m+tzjA6pkvG39stbDMdB+8OjMjVziGnONQqPk9obl5IcZOyZBLEf38HBlAv7I85eG3TJRDKwDqOvNMCGuilW6XCJXxI5g1kcN/WOelktG4QfxQeVKvyuFYFdSlzZ8+DbnAAvbxEx6gl6FxwerzgFUVGPfhIzrbRDuxgAMjRQEB3kvgFEp0RLqs2RLuqecDbttObJiEB48DyC65VVOgeSBd5UmmAXBavyuBTBSQJjprxdF5j006N29fCQm8mVMp/JlrUw5D2PECEqoEKDg/sCTBbnQR3uaEpbbNJOBQIDAQABAoH/FU0Ll5MmHzLI5dAEipebu5e0Fx3syIX99HilsO6/DdxZUIliP5/nwa2PjqQo4wNiKsO8KeJpxxkGSDs5S4qnW6E82WFHK8oV9VX0hyXhPmvVJGJmEhFsA7SKHMaX9yVU9WTBajeyH9iDUkmvP1fQdS2N3+r+77S+sh4Bhe0ZdJS2u1SBcNn0aWGWNAmNQPa9iLS9Rcm0YE0dElVTG8OShmGLOEtT7NiSxB02/fegs0k2KPO234Jkg38Dl9uLzqfmsiiQ0jdkPE2EMv1C4Bw8PosaWSm84jbe1+m/z3xpz+EWe2wsUl9d13YUZ9uPn7sd7GgWLvUwRAWdZn3/6sB1AoGBAPdJjEFRsT11XfN6Rax2FehuPGyx9f6+153lKKExRG5o20FTUglo8PytsS2lEc1VTdcQdnQLA+OQJ9eCQKDSDPF0/xL6u6l5NmFUVt23PO/4Vh8l4jDpY9yCqqZdv/NXtm3/q7p9PsWPa1NipD5yWAisFnfP/RVFA2FqGB6KzOvDAoGBAPVyMJG1o5YBF2+VVzmxi9erUZjcRY7rr5GiLi5RMCplsVX2QDHu9HjQiDMZ96YofF48ijjT6xI8HeM3wq4ti6Ki+nQj0mNRFjWrtCGjqhAmnUW1i00NWTkQmSy29niOuzlwDig0x80fFeQdfUr+JQRBXtz2Coy2+n0phtQnkeqXAoGAX463OSKFWBd7h7OvKtay3o+CoAog4iEDFwvPqIJuTDAO0e6Nm/9GaI4CEn5gI7WcD8hXzb3X8d7p1niJ2ttwjvnD+6SSUgn5/qBltOWzEZQoS38LKhV2Nmbz5LgDjdhOMbGDvdvaHuOt+Jn8l/j9ldRSFlh0Fv0FMmaaDWtbhpsCgYAg/bVLkJepbQIibHdp+NRsK8nu+wSaflF+CMeeUCliOYMJo+zjCVRw/M6h18ay3dRBsm99zfZV4CDATtlrIhIXzUJO5BW+ZcLPegHfV6lfPGF9YjaUJ8wrYHDGUt10PPMreEVe4CFPD/OYUuJpdxmwpbzDhIrMeTfYJQHLeHojhwKBgBn4smP3iNaoVbqeEe9Kq2PK9NAsZ4mFmO57lqtl1Cu1AnNmAXa82QXm607QGQus8jQvWTZqtcVjhrvOrQNUODre4pXxRgedYiNP/b+2SLkKH/hlgGH74W/8mmpcsKRGUSBb697PIlbfJdcP7kAXqupM0RN5A8+nSLeZo6KwJ8eb","json","GBK","MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA7Rew5YKutfPUjDa2BsoDng/8eG95Uuc8+gaz5Q//2jwjV8T9m+tzjA6pkvG39stbDMdB+8OjMjVziGnONQqPk9obl5IcZOyZBLEf38HBlAv7I85eG3TJRDKwDqOvNMCGuilW6XCJXxI5g1kcN/WOelktG4QfxQeVKvyuFYFdSlzZ8+DbnAAvbxEx6gl6FxwerzgFUVGPfhIzrbRDuxgAMjRQEB3kvgFEp0RLqs2RLuqecDbttObJiEB48DyC65VVOgeSBd5UmmAXBavyuBTBSQJjprxdF5j006N29fCQm8mVMp/JlrUw5D2PECEqoEKDg/sCTBbnQR3uaEpbbNJOBQIDAQAB","RSA2");
//                ((DefaultAlipayClient) alipayClient).getSigner().sign();
//                AlipayOpenAppQrcodeCreateRequest request = new AlipayOpenAppQrcodeCreateRequest();
//                request.setApiVersion("1.0");
//                request.setBizContent("{" +
//                        "\"url_param\":\"pages/goods/goods\"," +
//                        "\"query_param\":\"x=1\"," +
//                        "\"describe\":\"店铺\"" +
//                        "  }");
//                request.getClass();
//                try {
//                    Logger.i("AlipayClient-->","0");
//
//                    AlipayOpenAppQrcodeCreateResponse response = alipayClient.execute(request);
//                    Logger.i("AlipayClient-->","1");
//                    if(response.isSuccess()){
//                        toast("调用成功");
//                        Logger.i("AlipayClient-->","2");
//                        System.out.println("调用成功");
//                    } else {
//                        toast("调用失败");
//                        Logger.i("AlipayClient-->","3");
//                        System.out.println("调用失败");
//                    }
//                } catch (AlipayApiException e) {
//                    e.printStackTrace();
//                    Logger.i("AlipayClient-->","4");
//                }
//
//            }
//        }).start();

    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    public void onClick(View view) {

    }

    //隐藏所有Fragment
    public void hideAllFragment() {
        hideFragment(mineFragment);
        hideFragment(storeFragment);
        hideFragment(workSpaceFragment);
        hideFragment(homepageFragment);
    }

    private long clickTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (SystemClock.uptimeMillis() - clickTime <= 1500) {
                //如果两次的时间差＜1s，就不执行操作
                finishActivity();
            } else {
                //当前系统时间的毫秒值
                clickTime = SystemClock.uptimeMillis();
                toast("再按一次退出");
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);

    }
}
