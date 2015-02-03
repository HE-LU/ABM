package com.apiary.abm;


public class ABMConfig
{
	public static final boolean LOGS = true;
	public static final String FILE_BLUEPRINT_JSON = "blueprint_json";
	public static final String FILE_BLUEPRINT = "blueprint";
}


/* NOTES

package com.tuxilero.BCTest.client;

import com.tuxilero.BCTest.client.request.MockAPI;

import retrofit.RestAdapter;


public class APIHandler
{
	private static final String MOCK_API_URL = "http://demo4529274.mockable.io/";
	private static RestAdapter restAdapter;


	public static MockAPI getMockApi()
	{
		MockAPI mockAPI = null;
		try
		{
			if(restAdapter==null)
			{
				restAdapter = new RestAdapter.Builder()
						.setEndpoint(MOCK_API_URL)
						.build();
			}
			mockAPI = restAdapter.create(MockAPI.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return mockAPI;
	}
}











package com.tuxilero.BCTest.client.request;

import com.tuxilero.BCTest.client.entity.NoteEntity;

import retrofit.Callback;
import retrofit.http.GET;


public interface MockAPI
{
	@GET("/note")
	void getNote(Callback<NoteEntity> getNoteCallback);

	@GET("/noteSecond")
	void getNoteSecond(Callback<NoteSecondEntity> getNoteSecondCallback, NejakaEntita entita);
}














public void getNote()
	{
		MockAPI mockAPI = APIHandler.getMockApi();
		mockAPI.getNote(new Callback<NoteEntity>()
		{
			@Override
			public void success(NoteEntity note, Response response)
			{
				Toast.makeText(mRootView.getContext(), "Data found! : " + note.getNote(), Toast.LENGTH_LONG).show();
				Log.d("suc", "Data found: " + note.getNote());
			}


			@Override
			public void failure(RetrofitError error)
			{
				Log.d("e", error.getUrl());
				Log.d("e", error.getSuccessType().toString());
				Log.d("e", error.getKind().toString());
				Toast.makeText(mRootView.getContext(), "OPS, some kind of problem happened! :(", Toast.LENGTH_LONG).show();
			}
		});
	}







 */