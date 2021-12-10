package com.example.donorapp.Interface;


import com.example.donorapp.Model.AddDonorModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {
    String BASE_URL = "https://kafnbrbcch.execute-api.ap-south-1.amazonaws.com/Prod/";

   /* @POST("api/auth/token")
    Call<Login> getLogin(@Body Login loginRequest, @Header("Content-Type") String contenttype);

    @POST("api/Stock/AddStock")
    Call<AddStock> addStock(@Body AddStock addStock, @Header("Content-Type") String contenttype, @Header("Authorization") String token);

    @POST("api/Stock/BulkUplaod")
    Call<BulkUpload> bulkUpload(@Body BulkUpload bulkUpload, @Header("Content-Type") String contenttype, @Header("Authorization") String token);

    @POST("api/Stock/AddExpenses")
    Call<ExpenseModel> addExpenses(@Body ExpenseModel expenseModel, @Header("Content-Type") String contenttype, @Header("Authorization") String token);


    @POST("api/Stock/AddTesting")
    Call<AddTesting> addTesting(@Body AddTesting addTesting, @Header("Content-Type") String contenttype, @Header("Authorization") String token);


    @POST("api/Stock/AddCall")
    Call<AddCallModel> addCall(@Body AddCallModel addCallModel, @Header("Content-Type") String contenttype, @Header("Authorization") String token);


    @GET("api/Stock/ViewStock")
    Call<DiplayStockModel> viewStock(@Header("Content-Type") String contenttype, @Header("Authorization") String token);

    @POST("api/Stock/GetCallDetails")
    Call<DetailsModel> getCallDetails(@Body DetailsModel model, @Header("Content-Type") String contenttype, @Header("Authorization") String token);


    @POST("api/Stock/GetCallList")
    Call<GetCallList> getCallList(@Body GetCallList getCallList, @Header("Content-Type") String contenttype, @Header("Authorization") String token);


    @POST("api/Stock/FilteredStock")
    Call<ViewStockModel> viewFilteredStock(@Body ViewStockModel viewStockModel, @Header("Content-Type") String contenttype, @Header("Authorization") String token);

    @GET("api/Stock/GetAllHospitals")
    Call<GetHospitalList> getAllHospitalName(@Header("Content-Type") String contenttype, @Header("Authorization") String token);


    @GET("api/Stock/GetAllAssignedUser")
    Call<ServiceBoyModel> getAllServBoyName(@Header("Content-Type") String contenttype, @Header("Authorization") String token);

    @GET("api/Stock/GetAttendanceStatus")
    Call<GetAttendanceStatus> getAttendanceStatus(@Header("Content-Type") String contenttype, @Header("Authorization") String token);

    @POST("api/Stock/MarkAttendance")
    Call<GetAttendanceStatus> markAttendance(@Body GetAttendanceStatus model, @Header("Content-Type") String contenttype, @Header("Authorization") String token);


    @POST("api/Stock/AddRequisition")
    Call<SetRequisitionModel> addRequisition(@Body SetRequisitionModel model, @Header("Content-Type") String contenttype, @Header("Authorization") String token);

    @GET("api/Stock/GetApproveKilometerList")
    Call<ApproveKilometerModel> getApproveKilometerList(@Header("Content-Type") String contenttype, @Header("Authorization") String token);

    @POST("api/Stock/ApproveCallSettlement")
    Call<ApprovalModel> acceptApproval(@Body ApprovalModel model, @Header("Content-Type") String contenttype, @Header("Authorization") String token);


    @POST("api/Stock/Assign")
    Call<CallAssignModel> assignCall(@Body CallAssignModel callAssignModel, @Header("Content-Type") String contenttype, @Header("Authorization") String token);


    @POST("api/Stock/AddCallSettlement")
    Call<addPetrolModel> addCallSettlement(@Body addPetrolModel model, @Header("Content-Type") String contenttype, @Header("Authorization") String token);


    @GET("api/Stock/GetAttendanceStatus")
    Call<GetAttendanceStatus> getAttendance(@Header("Content-Type") String contenttype, @Header("Authorization") String token);

    @GET("api/Stock/GetProductRate")
    Call<ProductRateLists> getProductRate(@Header("Content-Type") String contenttype, @Header("Authorization") String token);


    @POST("api/Stock/CalculateCallSettlement")
    Call<SettlementModel> getSettlement(@Body SettlementModel model,@Header("Content-Type") String contenttype, @Header("Authorization") String token);

    @POST("api/Stock/ViewStockbyProductId")
    Call<ViewStockbyProductIdModel> viewProductBYID(@Body ViewStockbyProductIdModel model, @Header("Content-Type") String contenttype, @Header("Authorization") String token);

    @POST("api/auth/GetVersionDetails")
    Call<VersionInfoModel> versionInfo(@Body VersionInfoModel model, @Header("Content-Type") String contenttype);


    @POST("api/Stock/Accounts")
    Call<AdminAccounts> getAccountDetails(@Body AdminAccounts model, @Header("Content-Type") String contenttype, @Header("Authorization") String token);


    @GET("api/Stock/ViewCamp")
    Call<ViewCampModel> getViewCamp(@Header("Content-Type") String contenttype, @Header("Authorization") String token);


    @POST("api/Stock/AddCamp")
    Call<AddCampModel> addCamp(@Body AddCampModel addCampModel, @Header("Content-Type") String contenttype, @Header("Authorization") String token);

    @POST("api/Stock/AddDonor")
    Call<AddDonorModel> addDonor(@Body AddDonorModel addDonorModel, @Header("Content-Type") String contenttype, @Header("Authorization") String token);

    @POST("api/Stock/AddOrgniser")
    Call<AddOrganiserModel> addOrganiser(@Body AddOrganiserModel addOrganiserModel, @Header("Content-Type") String contenttype, @Header("Authorization") String token);*/
   @POST("api/Stock/AddDonor")
   Call<AddDonorModel> addDonor(@Body AddDonorModel addDonorModel, @Header("Content-Type") String contenttype, @Header("Authorization") String token);

}
