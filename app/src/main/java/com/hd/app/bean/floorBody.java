package com.hd.app.bean;

import java.util.List;

public class floorBody {
    /**
     * status : 1
     * message : 查询成功
     * result : [{"startWeek":1,"classRoomId":1,"saturday":0,"teacherName":"孙晓林","thursday":0,"sunday":0,"courseName":"机械制造工艺学","tuesday":0,"friday":0,"wednesday":5,"endWeek":10,"courseId":1,"doorId":101,"monday":1},{"startWeek":1,"classRoomId":1,"saturday":0,"teacherName":"聂晓根","thursday":0,"sunday":0,"courseName":"数控技术","tuesday":0,"friday":0,"wednesday":0,"endWeek":18,"courseId":2,"doorId":101,"monday":3},{"startWeek":1,"classRoomId":1,"saturday":1,"teacherName":"黄敏纯,唐旭晟","thursday":0,"sunday":0,"courseName":"机电装备设计","tuesday":0,"friday":0,"wednesday":0,"endWeek":12,"courseId":3,"doorId":101,"monday":5},{"startWeek":1,"classRoomId":1,"saturday":3,"teacherName":"唐旭晟,黄敏纯","thursday":0,"sunday":0,"courseName":"机电装备设计","tuesday":0,"friday":0,"wednesday":0,"endWeek":10,"courseId":4,"doorId":101,"monday":7},{"startWeek":1,"classRoomId":1,"saturday":0,"teacherName":"黄彬","thursday":9,"sunday":0,"courseName":"制造业信息化","tuesday":0,"friday":0,"wednesday":0,"endWeek":4,"courseId":5,"doorId":101,"monday":9},{"startWeek":1,"classRoomId":2,"saturday":0,"teacherName":"林有希,涂俊翔","thursday":0,"sunday":0,"courseName":"机械制造工艺学","tuesday":0,"friday":0,"wednesday":5,"endWeek":10,"courseId":22,"doorId":102,"monday":1},{"startWeek":1,"classRoomId":2,"saturday":0,"teacherName":"陈剑雄","thursday":0,"sunday":0,"courseName":"数控技术","tuesday":0,"friday":0,"wednesday":0,"endWeek":18,"courseId":23,"doorId":102,"monday":3},{"startWeek":1,"classRoomId":2,"saturday":0,"teacherName":"周至杰","thursday":0,"sunday":0,"courseName":"中国近现代史纲要","tuesday":0,"friday":5,"wednesday":0,"endWeek":16,"courseId":24,"doorId":102,"monday":5},{"startWeek":1,"classRoomId":2,"saturday":0,"teacherName":"周至杰","thursday":0,"sunday":0,"courseName":"中国近现代史纲要","tuesday":0,"friday":7,"wednesday":0,"endWeek":16,"courseId":25,"doorId":102,"monday":7},{"startWeek":1,"classRoomId":2,"saturday":0,"teacherName":"袁寒松","thursday":0,"sunday":0,"courseName":"财务报告(F7)","tuesday":0,"friday":0,"wednesday":0,"endWeek":8,"courseId":31,"doorId":102,"monday":9},{"startWeek":1,"classRoomId":3,"saturday":0,"teacherName":"林述温,顾天奇","thursday":0,"sunday":0,"courseName":"机械制造工艺学","tuesday":0,"friday":0,"wednesday":0,"endWeek":10,"courseId":42,"doorId":103,"monday":1},{"startWeek":1,"classRoomId":3,"saturday":0,"teacherName":"陈爱莲","thursday":0,"sunday":0,"courseName":"数学分析","tuesday":0,"friday":0,"wednesday":0,"endWeek":16,"courseId":43,"doorId":103,"monday":3},{"startWeek":1,"classRoomId":3,"saturday":0,"teacherName":"宋平","thursday":0,"sunday":0,"courseName":"中国近现代史纲要","tuesday":0,"friday":0,"wednesday":0,"endWeek":16,"courseId":44,"doorId":103,"monday":5},{"startWeek":1,"classRoomId":3,"saturday":0,"teacherName":"宋平","thursday":0,"sunday":0,"courseName":"中国近现代史纲要","tuesday":0,"friday":0,"wednesday":0,"endWeek":16,"courseId":45,"doorId":103,"monday":7},{"startWeek":1,"classRoomId":5,"saturday":0,"teacherName":"刘清海","thursday":0,"sunday":0,"courseName":"运筹学","tuesday":0,"friday":0,"wednesday":0,"endWeek":16,"courseId":57,"doorId":105,"monday":1},{"startWeek":1,"classRoomId":5,"saturday":0,"teacherName":"邓新国","thursday":0,"sunday":0,"courseName":"数据库系统原理","tuesday":0,"friday":1,"wednesday":0,"endWeek":14,"courseId":58,"doorId":105,"monday":3},{"startWeek":1,"classRoomId":5,"saturday":0,"teacherName":"程红举","thursday":0,"sunday":0,"courseName":"计算机网络","tuesday":5,"friday":0,"wednesday":0,"endWeek":8,"courseId":60,"doorId":105,"monday":5},{"startWeek":1,"classRoomId":5,"saturday":0,"teacherName":"程红举","thursday":0,"sunday":0,"courseName":"计算机网络","tuesday":7,"friday":0,"wednesday":0,"endWeek":8,"courseId":61,"doorId":105,"monday":7},{"startWeek":1,"classRoomId":6,"saturday":0,"teacherName":"黄利频","thursday":0,"sunday":0,"courseName":"工程经济学","tuesday":0,"friday":0,"wednesday":5,"endWeek":11,"courseId":77,"doorId":106,"monday":1},{"startWeek":1,"classRoomId":6,"saturday":0,"teacherName":"李明林","thursday":0,"sunday":0,"courseName":"理论力学A","tuesday":0,"friday":0,"wednesday":7,"endWeek":11,"courseId":78,"doorId":106,"monday":3},{"startWeek":1,"classRoomId":6,"saturday":0,"teacherName":"戴自航","thursday":1,"sunday":0,"courseName":"土力学","tuesday":0,"friday":5,"wednesday":0,"endWeek":11,"courseId":79,"doorId":106,"monday":5},{"startWeek":1,"classRoomId":6,"saturday":0,"teacherName":"蒋子平,郑柯,胡昌斌","thursday":0,"sunday":0,"courseName":"路基路面与交通工程","tuesday":0,"friday":0,"wednesday":0,"endWeek":13,"courseId":81,"doorId":106,"monday":9}]
     */

    private int status;
    private String message;
    private List<ResultBean> result;

    @Override
    public String toString() {
        return "floorBody{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        @Override
        public String toString() {
            return "ResultBean{" +
                    "startWeek=" + startWeek +
                    ", classRoomId=" + classRoomId +
                    ", saturday=" + saturday +
                    ", teacherName='" + teacherName + '\'' +
                    ", thursday=" + thursday +
                    ", sunday=" + sunday +
                    ", courseName='" + courseName + '\'' +
                    ", tuesday=" + tuesday +
                    ", friday=" + friday +
                    ", wednesday=" + wednesday +
                    ", endWeek=" + endWeek +
                    ", courseId=" + courseId +
                    ", doorId=" + doorId +
                    ", monday=" + monday +
                    '}';
        }

        /**
         * startWeek : 1
         * classRoomId : 1
         * saturday : 0
         * teacherName : 孙晓林
         * thursday : 0
         * sunday : 0
         * courseName : 机械制造工艺学
         * tuesday : 0
         * friday : 0
         * wednesday : 5
         * endWeek : 10
         * courseId : 1
         * doorId : 101
         * monday : 1
         */

        private int startWeek;
        private int classRoomId;
        private int saturday;
        private String teacherName;
        private int thursday;
        private int sunday;
        private String courseName;
        private int tuesday;
        private int friday;
        private int wednesday;
        private int endWeek;
        private int courseId;
        private int doorId;
        private int monday;

        public int getStartWeek() {
            return startWeek;
        }

        public void setStartWeek(int startWeek) {
            this.startWeek = startWeek;
        }

        public int getClassRoomId() {
            return classRoomId;
        }

        public void setClassRoomId(int classRoomId) {
            this.classRoomId = classRoomId;
        }

        public int getSaturday() {
            return saturday;
        }

        public void setSaturday(int saturday) {
            this.saturday = saturday;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public int getThursday() {
            return thursday;
        }

        public void setThursday(int thursday) {
            this.thursday = thursday;
        }

        public int getSunday() {
            return sunday;
        }

        public void setSunday(int sunday) {
            this.sunday = sunday;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public int getTuesday() {
            return tuesday;
        }

        public void setTuesday(int tuesday) {
            this.tuesday = tuesday;
        }

        public int getFriday() {
            return friday;
        }

        public void setFriday(int friday) {
            this.friday = friday;
        }

        public int getWednesday() {
            return wednesday;
        }

        public void setWednesday(int wednesday) {
            this.wednesday = wednesday;
        }

        public int getEndWeek() {
            return endWeek;
        }

        public void setEndWeek(int endWeek) {
            this.endWeek = endWeek;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public int getDoorId() {
            return doorId;
        }

        public void setDoorId(int doorId) {
            this.doorId = doorId;
        }

        public int getMonday() {
            return monday;
        }

        public void setMonday(int monday) {
            this.monday = monday;
        }
    }
}
