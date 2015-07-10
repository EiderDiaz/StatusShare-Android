/** 
 * Copyright (c) 2014 Kinvey Inc.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 * 
 */
package com.kinvey.sample.statusshare.fragments;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.api.client.json.GenericJson;
import com.kinvey.java.model.KinveyReference;
import com.kinvey.sample.statusshare.MainActivity;
import com.kinvey.sample.statusshare.R;
import com.kinvey.sample.statusshare.component.CommentAdapter;
import com.kinvey.sample.statusshare.model.UpdateEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author edwardf
 * @since 2.0
 */
public class UpdateDetailsFragment extends KinveyFragment{

    private ImageView image;
    private TextView text;
    private TextView author;
    private ListView commentList;

    private UpdateEntity entity;


    public static UpdateDetailsFragment newInstance(UpdateEntity entity){
        UpdateDetailsFragment frag = new UpdateDetailsFragment();
        frag.setEntity(entity);
        frag.setHasOptionsMenu(true);


        return frag;
    }

    private UpdateDetailsFragment(){}


    @Override
    public int getViewID() {
        return R.layout.fragment_update_details;
    }

    @Override
    public void bindViews(View v) {
        getActivity().getActionBar().setTitle("View Update");
        image = (ImageView) v.findViewById(R.id.update_image);
        text = (TextView) v.findViewById(R.id.update_text);
        author = (TextView) v.findViewById(R.id.update_author);
        commentList = (ListView) v.findViewById(R.id.update_comment_list);

        text.setTypeface(getRoboto());
        author.setTypeface(getRoboto());




    }

    @Override
    public void populateViews(){
        if (entity.getThumbnail() != null){
            image.setImageBitmap(entity.getThumbnail());
        }else{
            image.setVisibility(View.GONE);
        }
        text.setText(entity.getText());
        author.setText(entity.getAuthorName());

        List<KinveyReference> commentRefs = entity.getComments();
        if (commentRefs == null){
            return;
        }
        List<GenericJson> comments = new ArrayList<GenericJson>();

        for (KinveyReference kr : commentRefs){
            comments.add(kr.getResolvedObject());
        }

        CommentAdapter adapter = new CommentAdapter(getActivity(), comments,  getActivity().getLayoutInflater());
        commentList.setAdapter(adapter);


    }


    public UpdateEntity getEntity() {
        return entity;
    }

    public void setEntity(UpdateEntity entity) {
        this.entity = entity;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_view_update, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_comment:
                if (entity != null){
                    addComment();
                    return true;
                }

        }

        return super.onOptionsItemSelected(item);
    }

    public void addComment(){
        ((MainActivity) getActivity()).replaceFragment(CommentEditFragment.newInstance(entity), false);


    }
}
