package com.hd.app.bean;

import android.os.Parcelable;

import java.io.Serializable;

public class getDishBean  {

    @Override
    public String toString() {
        return "dishBody{" +
                "dishNum=" + dishNum +
                ", dish1=" + dish1 +
                ", dish2=" + dish2 +
                ", dish3=" + dish3 +
                ", dish4=" + dish4 +
                ", dish5=" + dish5 +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }

    /**
     * dishNum : 5
     * dish1 : {"seafood":0,"distance":407,"noodles":0,"latitude":119.192576,"spicy":1,"rice":0,"resName":"兰州拉面","oil":1,"balance":1,"flour":1,"name":"辣味泡椒什锦粉","canteen":"玫瑰园一楼","id":634,"longitude":26.053089}
     * dish2 : {"seafood":0,"distance":407,"noodles":0,"latitude":119.192576,"spicy":1,"rice":0,"resName":"兰州拉面","oil":1,"balance":1,"flour":1,"name":"辣味泡椒肉丝粉","canteen":"玫瑰园一楼","id":635,"longitude":26.053089}
     * dish3 : {"seafood":0,"distance":407,"noodles":0,"latitude":119.192576,"spicy":1,"rice":0,"resName":"兰州拉面","oil":1,"balance":1,"flour":1,"name":"辣味泡椒鸡肉粉","canteen":"玫瑰园一楼","id":636,"longitude":26.053089}
     * dish4 : {"seafood":1,"distance":407,"noodles":0,"latitude":119.192576,"spicy":1,"rice":0,"resName":"爱米渔","oil":1,"balance":1,"flour":1,"name":"酸辣鱼粉","canteen":"玫瑰园二楼","id":674,"longitude":26.053089}
     * dish5 : {"seafood":1,"distance":407,"noodles":0,"latitude":119.192576,"spicy":1,"rice":0,"resName":"爱米渔","oil":1,"balance":1,"flour":1,"name":"麻辣鱼粉","canteen":"玫瑰园二楼","id":676,"longitude":26.053089}
     * status : 1
     * message : 查询成功
     */

    private Integer dishNum;
    private Dish1Bean dish1;
    private Dish2Bean dish2;
    private Dish3Bean dish3;
    private Dish4Bean dish4;
    private Dish5Bean dish5;
    private Integer status;
    private String message;

    public Integer getDishNum() {
        return dishNum;
    }

    public void setDishNum(Integer dishNum) {
        this.dishNum = dishNum;
    }

    public Dish1Bean getDish1() {
        return dish1;
    }

    public void setDish1(Dish1Bean dish1) {
        this.dish1 = dish1;
    }

    public Dish2Bean getDish2() {
        return dish2;
    }

    public void setDish2(Dish2Bean dish2) {
        this.dish2 = dish2;
    }

    public Dish3Bean getDish3() {
        return dish3;
    }

    public void setDish3(Dish3Bean dish3) {
        this.dish3 = dish3;
    }

    public Dish4Bean getDish4() {
        return dish4;
    }

    public void setDish4(Dish4Bean dish4) {
        this.dish4 = dish4;
    }

    public Dish5Bean getDish5() {
        return dish5;
    }

    public void setDish5(Dish5Bean dish5) {
        this.dish5 = dish5;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class Dish1Bean {
        @Override
        public String toString() {
            return "Dish1Bean{" +
                    "seafood=" + seafood +
                    ", distance=" + distance +
                    ", noodles=" + noodles +
                    ", latitude=" + latitude +
                    ", spicy=" + spicy +
                    ", rice=" + rice +
                    ", resName='" + resName + '\'' +
                    ", oil=" + oil +
                    ", balance=" + balance +
                    ", flour=" + flour +
                    ", name='" + name + '\'' +
                    ", canteen='" + canteen + '\'' +
                    ", id=" + id +
                    ", longitude=" + longitude +
                    '}';
        }

        /**
         * seafood : 0
         * distance : 407
         * noodles : 0
         * latitude : 119.192576
         * spicy : 1
         * rice : 0
         * resName : 兰州拉面
         * oil : 1
         * balance : 1
         * flour : 1
         * name : 辣味泡椒什锦粉
         * canteen : 玫瑰园一楼
         * id : 634
         * longitude : 26.053089
         */

        private Integer seafood;
        private Integer distance;
        private Integer noodles;
        private double latitude;
        private Integer spicy;
        private Integer rice;
        private String resName;
        private Integer oil;
        private Integer balance;
        private Integer flour;
        private String name;
        private String canteen;
        private Integer id;
        private double longitude;

        public Integer getSeafood() {
            return seafood;
        }

        public void setSeafood(Integer seafood) {
            this.seafood = seafood;
        }

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }

        public Integer getNoodles() {
            return noodles;
        }

        public void setNoodles(Integer noodles) {
            this.noodles = noodles;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public Integer getSpicy() {
            return spicy;
        }

        public void setSpicy(Integer spicy) {
            this.spicy = spicy;
        }

        public Integer getRice() {
            return rice;
        }

        public void setRice(Integer rice) {
            this.rice = rice;
        }

        public String getResName() {
            return resName;
        }

        public void setResName(String resName) {
            this.resName = resName;
        }

        public Integer getOil() {
            return oil;
        }

        public void setOil(Integer oil) {
            this.oil = oil;
        }

        public Integer getBalance() {
            return balance;
        }

        public void setBalance(Integer balance) {
            this.balance = balance;
        }

        public Integer getFlour() {
            return flour;
        }

        public void setFlour(Integer flour) {
            this.flour = flour;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCanteen() {
            return canteen;
        }

        public void setCanteen(String canteen) {
            this.canteen = canteen;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }

    public static class Dish2Bean {
        @Override
        public String toString() {
            return "Dish2Bean{" +
                    "seafood=" + seafood +
                    ", distance=" + distance +
                    ", noodles=" + noodles +
                    ", latitude=" + latitude +
                    ", spicy=" + spicy +
                    ", rice=" + rice +
                    ", resName='" + resName + '\'' +
                    ", oil=" + oil +
                    ", balance=" + balance +
                    ", flour=" + flour +
                    ", name='" + name + '\'' +
                    ", canteen='" + canteen + '\'' +
                    ", id=" + id +
                    ", longitude=" + longitude +
                    '}';
        }

        /**
         * seafood : 0
         * distance : 407
         * noodles : 0
         * latitude : 119.192576
         * spicy : 1
         * rice : 0
         * resName : 兰州拉面
         * oil : 1
         * balance : 1
         * flour : 1
         * name : 辣味泡椒肉丝粉
         * canteen : 玫瑰园一楼
         * id : 635
         * longitude : 26.053089
         */

        private Integer seafood;
        private Integer distance;
        private Integer noodles;
        private double latitude;
        private Integer spicy;
        private Integer rice;
        private String resName;
        private Integer oil;
        private Integer balance;
        private Integer flour;
        private String name;
        private String canteen;
        private Integer id;
        private double longitude;

        public Integer getSeafood() {
            return seafood;
        }

        public void setSeafood(Integer seafood) {
            this.seafood = seafood;
        }

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }

        public Integer getNoodles() {
            return noodles;
        }

        public void setNoodles(Integer noodles) {
            this.noodles = noodles;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public Integer getSpicy() {
            return spicy;
        }

        public void setSpicy(Integer spicy) {
            this.spicy = spicy;
        }

        public Integer getRice() {
            return rice;
        }

        public void setRice(Integer rice) {
            this.rice = rice;
        }

        public String getResName() {
            return resName;
        }

        public void setResName(String resName) {
            this.resName = resName;
        }

        public Integer getOil() {
            return oil;
        }

        public void setOil(Integer oil) {
            this.oil = oil;
        }

        public Integer getBalance() {
            return balance;
        }

        public void setBalance(Integer balance) {
            this.balance = balance;
        }

        public Integer getFlour() {
            return flour;
        }

        public void setFlour(Integer flour) {
            this.flour = flour;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCanteen() {
            return canteen;
        }

        public void setCanteen(String canteen) {
            this.canteen = canteen;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }

    public static class Dish3Bean {
        @Override
        public String toString() {
            return "Dish3Bean{" +
                    "seafood=" + seafood +
                    ", distance=" + distance +
                    ", noodles=" + noodles +
                    ", latitude=" + latitude +
                    ", spicy=" + spicy +
                    ", rice=" + rice +
                    ", resName='" + resName + '\'' +
                    ", oil=" + oil +
                    ", balance=" + balance +
                    ", flour=" + flour +
                    ", name='" + name + '\'' +
                    ", canteen='" + canteen + '\'' +
                    ", id=" + id +
                    ", longitude=" + longitude +
                    '}';
        }

        /**
         * seafood : 0
         * distance : 407
         * noodles : 0
         * latitude : 119.192576
         * spicy : 1
         * rice : 0
         * resName : 兰州拉面
         * oil : 1
         * balance : 1
         * flour : 1
         * name : 辣味泡椒鸡肉粉
         * canteen : 玫瑰园一楼
         * id : 636
         * longitude : 26.053089
         */

        private Integer seafood;
        private Integer distance;
        private Integer noodles;
        private double latitude;
        private Integer spicy;
        private Integer rice;
        private String resName;
        private Integer oil;
        private Integer balance;
        private Integer flour;
        private String name;
        private String canteen;
        private Integer id;
        private double longitude;
        public Integer getSeafood() {
            return seafood;
        }

        public void setSeafood(Integer seafood) {
            this.seafood = seafood;
        }

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }

        public Integer getNoodles() {
            return noodles;
        }

        public void setNoodles(Integer noodles) {
            this.noodles = noodles;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public Integer getSpicy() {
            return spicy;
        }

        public void setSpicy(Integer spicy) {
            this.spicy = spicy;
        }

        public Integer getRice() {
            return rice;
        }

        public void setRice(Integer rice) {
            this.rice = rice;
        }

        public String getResName() {
            return resName;
        }

        public void setResName(String resName) {
            this.resName = resName;
        }

        public Integer getOil() {
            return oil;
        }

        public void setOil(Integer oil) {
            this.oil = oil;
        }

        public Integer getBalance() {
            return balance;
        }

        public void setBalance(Integer balance) {
            this.balance = balance;
        }

        public Integer getFlour() {
            return flour;
        }

        public void setFlour(Integer flour) {
            this.flour = flour;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCanteen() {
            return canteen;
        }

        public void setCanteen(String canteen) {
            this.canteen = canteen;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }

    public static class Dish4Bean {
        @Override
        public String toString() {
            return "Dish4Bean{" +
                    "seafood=" + seafood +
                    ", distance=" + distance +
                    ", noodles=" + noodles +
                    ", latitude=" + latitude +
                    ", spicy=" + spicy +
                    ", rice=" + rice +
                    ", resName='" + resName + '\'' +
                    ", oil=" + oil +
                    ", balance=" + balance +
                    ", flour=" + flour +
                    ", name='" + name + '\'' +
                    ", canteen='" + canteen + '\'' +
                    ", id=" + id +
                    ", longitude=" + longitude +
                    '}';
        }

        /**
         * seafood : 1
         * distance : 407
         * noodles : 0
         * latitude : 119.192576
         * spicy : 1
         * rice : 0
         * resName : 爱米渔
         * oil : 1
         * balance : 1
         * flour : 1
         * name : 酸辣鱼粉
         * canteen : 玫瑰园二楼
         * id : 674
         * longitude : 26.053089
         */

        private Integer seafood;
        private Integer distance;
        private Integer noodles;
        private double latitude;
        private Integer spicy;
        private Integer rice;
        private String resName;
        private Integer oil;
        private Integer balance;
        private Integer flour;
        private String name;
        private String canteen;
        private Integer id;
        private double longitude;

        public Integer getSeafood() {
            return seafood;
        }

        public void setSeafood(Integer seafood) {
            this.seafood = seafood;
        }

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }

        public Integer getNoodles() {
            return noodles;
        }

        public void setNoodles(Integer noodles) {
            this.noodles = noodles;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public Integer getSpicy() {
            return spicy;
        }

        public void setSpicy(Integer spicy) {
            this.spicy = spicy;
        }

        public Integer getRice() {
            return rice;
        }

        public void setRice(Integer rice) {
            this.rice = rice;
        }

        public String getResName() {
            return resName;
        }

        public void setResName(String resName) {
            this.resName = resName;
        }

        public Integer getOil() {
            return oil;
        }

        public void setOil(Integer oil) {
            this.oil = oil;
        }

        public Integer getBalance() {
            return balance;
        }

        public void setBalance(Integer balance) {
            this.balance = balance;
        }

        public Integer getFlour() {
            return flour;
        }

        public void setFlour(Integer flour) {
            this.flour = flour;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCanteen() {
            return canteen;
        }

        public void setCanteen(String canteen) {
            this.canteen = canteen;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }

    public static class Dish5Bean {
        @Override
        public String toString() {
            return "Dish5Bean{" +
                    "seafood=" + seafood +
                    ", distance=" + distance +
                    ", noodles=" + noodles +
                    ", latitude=" + latitude +
                    ", spicy=" + spicy +
                    ", rice=" + rice +
                    ", resName='" + resName + '\'' +
                    ", oil=" + oil +
                    ", balance=" + balance +
                    ", flour=" + flour +
                    ", name='" + name + '\'' +
                    ", canteen='" + canteen + '\'' +
                    ", id=" + id +
                    ", longitude=" + longitude +
                    '}';
        }

        /**
         * seafood : 1
         * distance : 407
         * noodles : 0
         * latitude : 119.192576
         * spicy : 1
         * rice : 0
         * resName : 爱米渔
         * oil : 1
         * balance : 1
         * flour : 1
         * name : 麻辣鱼粉
         * canteen : 玫瑰园二楼
         * id : 676
         * longitude : 26.053089
         */

        private Integer seafood;
        private Integer distance;
        private Integer noodles;
        private double latitude;
        private Integer spicy;
        private Integer rice;
        private String resName;
        private Integer oil;
        private Integer balance;
        private Integer flour;
        private String name;
        private String canteen;
        private Integer id;
        private double longitude;

        public Integer getSeafood() {
            return seafood;
        }

        public void setSeafood(Integer seafood) {
            this.seafood = seafood;
        }

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }

        public Integer getNoodles() {
            return noodles;
        }

        public void setNoodles(Integer noodles) {
            this.noodles = noodles;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public Integer getSpicy() {
            return spicy;
        }

        public void setSpicy(Integer spicy) {
            this.spicy = spicy;
        }

        public Integer getRice() {
            return rice;
        }

        public void setRice(Integer rice) {
            this.rice = rice;
        }

        public String getResName() {
            return resName;
        }

        public void setResName(String resName) {
            this.resName = resName;
        }

        public Integer getOil() {
            return oil;
        }

        public void setOil(Integer oil) {
            this.oil = oil;
        }

        public Integer getBalance() {
            return balance;
        }

        public void setBalance(Integer balance) {
            this.balance = balance;
        }

        public Integer getFlour() {
            return flour;
        }

        public void setFlour(Integer flour) {
            this.flour = flour;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCanteen() {
            return canteen;
        }

        public void setCanteen(String canteen) {
            this.canteen = canteen;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }
}
