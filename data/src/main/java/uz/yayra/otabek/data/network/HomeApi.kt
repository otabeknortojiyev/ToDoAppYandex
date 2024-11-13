package uz.yayra.otabek.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import uz.yayra.otabek.common.request.ToDoRequest
import uz.yayra.otabek.common.response.ToDoResponse

interface HomeApi {

    @GET("list")
    suspend fun getAll(): Response<ToDoResponse.GetAll>

    @POST("list")
    suspend fun add(@Body data: ToDoRequest.AddToDo): Response<ToDoResponse.Post>

    @PUT("list/{id}")
    suspend fun update(@Path("id") todoId: String, @Body data: ToDoRequest.UpdateToDo): Response<ToDoResponse.Update>

    @DELETE("list/{id}")
    suspend fun delete(@Path("id") todoId: String): Response<ToDoResponse.Delete>
}