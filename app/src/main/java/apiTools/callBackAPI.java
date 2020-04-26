package apiTools;

import com.hd.app.bean.ResultRoom;
import com.hd.app.bean.floorBody;
import com.hd.app.bean.getDishBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface callBackAPI {
    @POST("dishes/getDishes")
    Call<getDishBean> postWithParamsDishes(@QueryMap Map<String,Object> params);


    @POST("coursetable/getcoursetable")
    Call<ResultRoom> postWithParamsFloors(@QueryMap Map<String,Object> params);
}
