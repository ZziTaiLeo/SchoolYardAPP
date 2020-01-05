package com.hd.app;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.ocnyang.pagetransformerhelp.BannerItemBean;
import com.ocnyang.pagetransformerhelp.BannerViewPager;
import com.ocnyang.pagetransformerhelp.ImageLoaderInterface;
import com.ocnyang.pagetransformerhelp.transformer.AccordionTransformer;

import java.util.ArrayList;
import java.util.List;

import module.SpotInformation;

public class SpotConcreteActivity extends AppCompatActivity {


    public static final String SPOT_Name ="spotname";
    public static List<SpotInformation> spotInformationList = new ArrayList<>();
    private String name;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private ActionBar actionBar;
    private TextView spotInformationText;
    private BannerViewPager soptPager;
    private Integer[] imgArray= null;
    private FloatingActionButton takeThereIcon;
    private double spotLatitude;
    private double spotLogitude;

    private String spotName = new String() ;
    private String spotId = new String();
    private String spotText = new String() ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_concrete);
        initSpotInformatin();
        initView();
    }

    private void initSpotInformatin() {

        Integer[] imgIDArray =null;
        String text = null;
        SpotInformation information = null;



        imgIDArray = new Integer[]{R.mipmap.fuyouge1,R.mipmap.fuyouge2,R.mipmap.fuyouge3,R.mipmap.fuyouge4,R.mipmap.fuyouge5};
        text = "福友阁是由福州大学福州校友会出资建成，整个建筑位于福大南门侧，夏天时刻，周围的荷花都盛开了，这便成了拍照打卡圣地。福友阁充分体现了校友会对母校的鼎力支持和广大福州校友心系母校、回馈母校的责任心和爱心，而我们新一代福大人也应以他们为榜样，积极向上，未来学有所成，以优秀的成绩回报母校。";
        information = new SpotInformation("1","福州大学福友阁",imgIDArray,text,26.059707,119.206875);
        spotInformationList.add(information);

        imgIDArray = new Integer[]{R.mipmap.library1,R.mipmap.library2,R.mipmap.library3,R.mipmap.library4,R.mipmap.library5,R.mipmap.library6,R.mipmap.library7};
        text = " 福州大学图书馆与学校同步创建于1958年。伴随着学校的建设和发展，图书馆的基本建设和各项工作都得到了较大的发展，已经成为一座馆藏丰富、环境舒适、设施齐全、管理先进、服务到位的现代化程度较高的大中型高校图书馆。\n" +
                "一、藏书概况\n" +
                "      根据学校的专业设置、重点学科以及教学科研发展方向，图书馆除了收集人文、社会科学及管理科学等多种类型和载体的综合性文献外，还重点收集了物理化学、电机电器与控制、材料科学与工程、机械电子工程、石油化工、生物工程等学科门类及其相关的基础理论和应用技术方面的文献。截止2019年初，馆藏纸质中外文图书350万册，中外文纸质期刊2404种，期刊合订本 290132册。电子图书367万册，中外文数据库64种，自建特色数据库7个。馆内大型检索工具收集比较齐全，其中《化学文摘》、《工程索引》等收集齐全，成为福建省工程技术科学文献中心。\n" +
                "二、馆舍与设备\n" +
                "      图书馆现有一个主馆，七个分馆。旗山校区主馆是福州大学新校区的标志性建筑，于2006年3月落成并投入使用，馆舍坐落于新校区中心区，呈正方形，主楼地上为五层，面积35500平方米，于2008年获得中国工程建设鲁班奖，这是建筑上的一个重要奖项。\n" +
                "      图书馆目前馆藏全部采用计算机管理。1999年选用了易信图书馆自动化管理系统，实现了办公管理和书刊采购、编目、典藏、流通、检索等计算机网络化管理，全校师生可通过校园网便捷地访问图书馆主页，在网上检索馆藏书刊目录、数据库等电子资源。图书馆现有工作人员用机150多台，检索用机26台，小型机3台，PC服务器60多台，投影机9台，LED显示设备6套，门禁系统2套。图书馆拥有4套存储系统，存储容量达240TB，能为教学、科研提供优质的数据库检索、全文下载等服务。\n" +
                "三、管理机制\n" +
                "      截止2019年1月，图书馆共有在编职工104名，编外用工19名。在正式工作人员中，硕士以上44人，大学本科56人；高级职称17人，中级职称73人。馆内分设8个部（室）。\n" +
                "在制度建设方面，我馆坚持精简、统一、效能原则，建立办事高效、运转协调、行为规范的管理体系，做好建章立制工作。2004年制定并实施了《福州大学图书馆规章制度汇编》，内容涉及岗位职责、业务工作、读者服务、行政及设备管理和安全卫生等方面。2017年进行了规章制度的全面修订，管理工作的标准化、规范化和科学化程度进一步提升。\n" +
                "四、服务特色\n" +
                "      图书馆在提高教职工服务意识的基础上，进一步规范服务程序，调整服务结构，拓展服务思路，强化服务功能，实施传统服务与现代服务双轨制；建立健全服务监督机制，通过不定期地与读者交流反馈，改进服务技能，提高服务效率。\n" +
                "图书馆现有4936个阅览座位，共设14个服务窗口，书刊借阅采取全天候、全开架的一站式服务模式。为方便读者，图书馆购置了自助检索机、自助借还机等设备。设立了总咨询台，实现了服务与现场咨询的无缝对接。为创新服务，图书馆还利用参考咨询台、留言板、电话、微博、微信、文献传输等，为读者提供零距离服务。2017年8月，图书馆启动RFID项目建设。目前，通过RFID项目建设，可以实现流通图书查找定位导航，自助借书，自助还书，图书预约自助取书，在架图书盘点、查错等功能，实现图书流通的智能管理。为培养读者的读书兴趣以及更加快捷使用图书馆，图书馆开展了名著导读，影视鉴赏，数据库使用方法培训等活动。近年来，图书馆共举办了“嘉锡讲坛”525场，数据库培训254场。\n" +
                "      图书馆加强福建省高校数字图书馆（FULink）建设，实现文献信息资源共享。截至2019年初，福建省高校数字图书馆已整合了53所联盟馆的6500多万册图书，以及300多个中外文数据库。同时还能检索到全国700多家图书馆的各类电子文献资源。FULink整合的元数据数量包括：学术文献资源6.5亿篇、中文图书书目480万条、中文期刊6420万篇、外文期刊10872万篇、中文报纸7000万篇、中文学位论文500万篇、外文学位论文250万篇、开放学术资源4000万篇。2018年度，为53个成员馆读者提供368,3070次统一检索服务，文献下载1,645,815篇；提供1,962,848次文献传递服务，(其中文献提供405,320次，移动FULink 1,557,032次），实现了文献资源保障。  \n" +
                "     2007年 3月13日，福州大学图书馆获得教育部授权，成为具有部级查新资质的查新机构，即教育部科技查新工作站（L22）。查新站面向高校、科研院所、政府机构、企业等开展科技查新与咨询服务。截止目前，已完成包括国家\"863高科技项目\"在内的国家及省、部级科研立项、成果鉴定、申报奖励和专利申请等项目在内的科技查新项目共8000多件。\n" +
                "      自2003年图书馆取得情报学硕士点以来，已培养了100多名情报学研究生。共申请研究课题60多项，发表论文300多篇。为全校本科生与研究生开设公共课三门，每年的选课学生达2500多人次；为情报学专业研究生开设课程17门，其中专业学位课8门，选修课9门。已形成了良好的教学科研一体化态势。";
        information = new SpotInformation("2","图书馆",imgIDArray,text,26.064811,119.204315);
        spotInformationList.add(information);


        imgIDArray = new Integer[]{R.mipmap.honghui1,R.mipmap.honghui2};
        text = "福州大学宏晖文体综合馆由福建宏晖实业集团有限公司捐赠1000多万元所建。位于旗山校区南门北侧，是一座集体育运动、毕业迎新、各类晚会、大型学术会议于一体的多功能综合性场馆。以背邻的火山公园为依托，融入天然石景为元素进行设计创作，历时200多天建造而成。总占地面积1万多平米，建筑面积约3000平方米，建筑总造价达到1400万元。馆内可同时容纳2500多人，是学校目前最大的综合性场馆，也是福州大学60周年纪念大会的主场馆。";
        information = new SpotInformation("3","宏晖文体综合馆",imgIDArray,text,26.057833,119.203511);
        spotInformationList.add(information);


        imgIDArray = new Integer[]{R.mipmap.qcgc1};
        text = "青春广场位于福大生活一区，是每年福大跨年晚会的举办地，每年的12月31号，这里都会挤满了人，一起高喊倒计时，观赏烟花，许下自己的心愿。";
        information = new SpotInformation("4","青春广场",imgIDArray,text,26.062551,119.198013);
        spotInformationList.add(information);


        imgIDArray = new Integer[]{R.mipmap.sutuo1,R.mipmap.sutuo2,R.mipmap.sutuo3,R.mipmap.sutuo4};
        text = "  素质拓展中心是福大很多学生部门的大本营，社团活动为福大学子注入了很多青春活力，在这里，福大学子们可以在课余参加活动放松身心，也可以结交很多新朋友提高自己的社交能力，可以说是最受福大学子欢迎的地方之一了。\n" +
                "  素质拓展中心最美的时候是每年5-6月份，门口的蓝楹花开满了枝丫，十分美丽。";
        information = new SpotInformation("5","素质拓展中心",imgIDArray,text,26.062596,119.202128);
        spotInformationList.add(information);

//        String spotName = "东三教学楼";
//        String spotId = "1";
//        String spotText = "东3教学楼一共五层，每层标配两间厕所与两间开水房，理工科专业课和英语相关课程多排在东3，教室间有小隔间可供同学们自习。 ";
//        Integer[] imgIDArray ={R.drawable.east_3_1,R.drawable.east_3_2};
//        SpotInformation spotInformation = new SpotInformation(spotId,spotName,imgIDArray,spotText,26.0646793692,119.2042646576);
//        spotInformationList.add(spotInformation);
    }

    private void initView()
    {
        Intent intent = getIntent();
        name = intent.getStringExtra(SPOT_Name);//获取主页面传过来的景点名


        for(SpotInformation spotInformation:spotInformationList)
        {

            Log.d("bbbbbb", spotInformation.getSpotID());
            if(name.equals(spotInformation.getSpotName()))
            {
                spotName = spotInformation.getSpotName();
                spotId = spotInformation.getSpotID();
                imgArray = spotInformation.getSpotImageID();
                spotText = spotInformation.getSpotInformation();
                spotLatitude = spotInformation.getSpotLatitude();
                spotLogitude = spotInformation.getSpotLongitude();
            }
        }

        takeThereIcon = (FloatingActionButton)findViewById(R.id.take_there_icon);
        /**
         * 点击去那里按钮，跳转进入路线规划界面
         */
        takeThereIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpotConcreteActivity.this, NavigationActivity.class);
                intent.putExtra("action","1");
                intent.putExtra("latitude",spotLatitude);
                intent.putExtra("logitude",spotLogitude);
                intent.putExtra("spotName",spotName);
                startActivity(intent);
            }
        });







        soptPager = (BannerViewPager) findViewById(R.id.spot_pager);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        collapsingToolbar =(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolBar);
        spotInformationText = (TextView)findViewById(R.id.spot_information_text);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        collapsingToolbar.setTitle(spotName);

        /**
         * 轮播图
         */
        // 把数组交给图片展播组件

        soptPager.setData(getViewPagerDatas(),//设置数据
                new ImageLoaderInterface() {//设置图片加载器
                    @Override
                    public void displayImage(Context context, Object imgPath, ImageView imageView) {
                        Glide.with(context).load(imgPath).into(imageView);
                    }
                }).setPageTransformer(new AccordionTransformer())//设置切换效果
                .setAutoPlay(true)//设置是否自动播放
                .setOnBannerItemClickListener(new BannerViewPager.OnBannerItemClickListener() {//设置item的监听事件
                    @Override
                    public void OnClickLister(View view, int currentItem) {


                        LayoutInflater inflater = LayoutInflater.from(SpotConcreteActivity.this);

                        View imgEntryView = inflater.inflate(R.layout.dialog_photo_entry, null); // 加载自定义的布局文件

                        PhotoView img = (PhotoView) imgEntryView.findViewById(R.id.large_image);
                        img.enable();//图片缩放手势允许
                        img.setImageResource(imgArray[currentItem]); // 自己的图片设置方法

                        final MyDialog dialog = new MyDialog(SpotConcreteActivity.this, 0, 0, imgEntryView, R.style.DialogTheme);
                        dialog.setCancelable(true);
                        // 自定义dialog
                        Button close = (Button)imgEntryView.findViewById(R.id.dialog_close);

                        dialog.show();

                        Window dialogWindow = dialog.getWindow();
                        WindowManager mWindowManager = getWindowManager();
                        Display mDisplay = mWindowManager.getDefaultDisplay();
                        WindowManager.LayoutParams mWindowParam = dialogWindow.getAttributes();
                        mWindowParam.height=(int)(mDisplay.getHeight()*0.5);
                        mWindowParam.width =(int)(mDisplay.getWidth()*0.9);
                        dialogWindow.setAttributes(mWindowParam);

// 点击布局文件（也可以理解为点击大图）后关闭dialog，这里的dialog不需要按钮

                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                            }
                        });

                    }
                })
                .setHaveTitle(false);//设置是否显示标题
        spotInformationText.setText(spotText);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            finish();
            return true;
        }
           return super.onOptionsItemSelected(item);
    }

    private List<BannerItemBean> getViewPagerDatas() {
        List<BannerItemBean> pagerItemBeanList = new ArrayList<>(imgArray.length);

        for (int i = 0; i < imgArray.length; i++) {
            pagerItemBeanList.add(new BannerItemBean(imgArray[i], ""));
        }
        return pagerItemBeanList;
    }

    public class MyDialog extends Dialog {
        //    style引用style样式
        public MyDialog(Context context, int width, int height, View layout, int style) {

            super(context, style);

            setContentView(layout);

            Window window = getWindow();

            WindowManager.LayoutParams params = window.getAttributes();

            params.gravity = Gravity.CENTER;

            window.setAttributes(params);
        }
    }



}
