package com.elitzur_software.momentekenmobile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.elitzur_software.momentekenmobile.R;

import data.TestChapter;
import data.TestItem;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.support.v7.widget.AppCompatButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChapterActivity2 extends ActionBarActivity implements TaskCycle {

    private ListView itemsListView;
    private TestChapter chapter;
    // uri for the captured image
    private Uri imgUri;
    // i needed item that the listener will know
    private TestItem globalItem;
    // i needed comment that the listener will know
    private EditText commentEditText;
    private ImageButton currentOkBtn;
    private ImageButton currentNotOkBtn;
    private int imageNumber;
    private MomentekenApp application;
    private MySQLiteHelper mySql;
    private boolean safety;
    private ActionBarDrawerToggle toggle;
    public static final int CAMERA_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter2);
        application = (MomentekenApp)getApplication();
        mySql = application.getMySQLhelper();
        // Find the graphical components from the layout xml file and
        // assign it to the matching variables.
        getComponents();
        // Fill the components with data
        initComponents();
        // sets the activity title to the chapter's name
        // for the title of the action bar in the activity
        setTitle(chapter.getTemplate().getTitle());
        safety = chapter.getTemplate().getTitle().equalsIgnoreCase("אישורי בטיחות")?true:false;
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        // override the action bar back button! must have the lines on onCreate:
        // 		ActionBar actionBar = getSupportActionBar();
        //		actionBar.setDisplayHomeAsUpEnabled(true);
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        String toastMsg = "";
        for( TestItem item : chapter.getItems() ) {
            if( item.isOk()) {
                continue;
            } else if( item.isFirst()) {
                toastMsg += item.getTemplateItem().getItemNumber() +" - לא סומן\n";
            } else {
                if( !safety && (item.getImage1Code() == null && item.getImage2Code() == null &&
                        (item.getCommnet() == null || item.getCommnet().length() == 0)) ) {
                    toastMsg += item.getTemplateItem().getItemNumber() +" - חובה לצלם תמונה או להזין הערה בפריט המסומן כלא תקין!\n";
                }
            }
        }

        if (toastMsg.length() > 1) {
            new AlertDialog.Builder(this)
                    .setTitle("הפריטים הבאים לא מולאו באופן תקין:")
                    .setMessage(toastMsg)
                    .setPositiveButton("צא בכל זאת", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ChapterActivity2.super.onBackPressed();
                        }
                    })
                    .setNegativeButton("אני אתקן עכשיו", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(R.drawable.warning)
                    .show();
        } else {
            super.onBackPressed();
        }


    }

    private void getComponents() {
        itemsListView = (ListView)findViewById(R.id.testsList);
    }

    private void initComponents() {
        int chapterPosition = getIntent().getIntExtra("chapterPosition", 0);
        chapter = SessionGlobalData.getInstance().getTest().getChapters().get(chapterPosition);

        ItemsListAdapter adapter = new ItemsListAdapter(this,
                R.layout.single_item,
                chapter.getItems());
        itemsListView.setAdapter(adapter);

    }

    /**
     * This inner class represent the adapter that adapts between the chapter's list
     * of items to the graphical listView
     * @author Shira Elitzur
     *
     */
    private class ItemsListAdapter extends ArrayAdapter<TestItem> {

        private int layout;
        private List<TestItem> items;
        ViewHolder holder;

        public ItemsListAdapter(Context context, int layout,
                                List<TestItem> items) {
            super(context, layout, items);
            this.layout = layout;
            this.items = items;
        }

        /**
         * This method called for each item of the list on each time it appears on the screen.
         * Means, that during scroll it get called again and again!
         * On a complex list view as this one, when pressing on buttons of the item affects the
         * item right away i must keep this information and check before the draw how i should draw
         * this specific item.
         * For example if can choose for each item between 'v' or 'x', than i must check on any call
         * of getView if the item is 'v' or 'x' or it is still neutrally.
         * So there are two important points to take care of when i want my list to change the way it looks:
         * 1. The events methods of the buttons ( Where if one component affects another component
         * i should send the other component as tag of the first in order to know him in the method ).
         * 2. At the beginning of the getView method where the items get draws - to make sure that it draws in
         * the way it should ( In that case i must keep some status on the actual list item, like i keep here the
         * level of the item on the actual TestItem ).
         */
        @Override
        public View getView(int position, View row, ViewGroup parent) {

            TestItem item;

            if ( row == null ) {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(layout, parent, false);

                holder = new ViewHolder();
                // Find the components and assign to the line's holder
                holder.number = (TextView)row.findViewById(R.id.numberText);
                holder.title = (TextView)row.findViewById(R.id.titleText);
                holder.image = (ImageButton)row.findViewById(R.id.cameraBtn);
                holder.gallery = (ImageButton)row.findViewById(R.id.galleryBtn);
                holder.comment = (ImageButton)row.findViewById(R.id.commentBtn);
                holder.ok = (ImageButton)row.findViewById(R.id.okBtn1);
                holder.notOk = (ImageButton)row.findViewById(R.id.notOkBtn);
                holder.levelsBtnLayout = (LinearLayout)row.findViewById(R.id.levelsButtonsLayout);

                holder.zero = (Button)row.findViewById(R.id.zeroButton);
                holder.one = (Button)row.findViewById(R.id.oneButton);
                holder.two = (Button)row.findViewById(R.id.twoButton);

                // Set listeners to the buttons
                ItemListener listener = new ItemListener();

                holder.zero.setOnClickListener(listener);
                holder.one.setOnClickListener(listener);
                holder.two.setOnClickListener(listener);

                holder.title.setOnClickListener(listener);

                holder.ok.setOnClickListener(listener);
                holder.notOk.setOnClickListener(listener);
                holder.image.setOnClickListener(listener);
                holder.gallery.setOnClickListener(listener);
                holder.comment.setOnClickListener(listener);
                // The important part of the holder trick - setting the tag to the row
                // let us use the components again when the row gets update without
                // using findViewById again
                row.setTag(holder);
            } else {
                // Like that
                holder = (ViewHolder)row.getTag();
            }

            item = items.get(position);
            if(safety) {
                if(item.getTemplateItem().getArea() == null ) {
                    holder.title.setText(item.getTemplateItem().getTitle());
                } else {
                    holder.title.setText(item.getTemplateItem().getArea());
                }
            } else {
                holder.title.setText(item.getTemplateItem().getArea());
            }

            holder.number.setText(item.getTemplateItem().getItemNumber());



            if( item.isLevel() ) {
                holder.levelsBtnLayout.setVisibility(LinearLayout.VISIBLE);
            } else {
                holder.levelsBtnLayout.setVisibility(LinearLayout.GONE);
            }



            if( item.isFirst() ) {
                holder.number.setTextColor(getResources().getColor(android.R.color.white));
                holder.ok.setBackgroundResource(R.drawable.ok_cancel_button_shape);
                holder.notOk.setBackgroundResource(R.drawable.ok_cancel_button_shape);
            } else {
                if( item.isOk() ) {
                    holder.number.setTextColor(getResources().getColor(R.color.ok_text_color));
                    holder.ok.setBackgroundResource(R.drawable.ok_button_shape);
                    holder.notOk.setBackgroundResource(R.drawable.ok_cancel_button_shape);
                } else {
                    holder.number.setTextColor(getResources().getColor(R.color.notok_text_color));
                    holder.ok.setBackgroundResource(R.drawable.ok_cancel_button_shape);
                    //holder.notOk.setBackgroundResource(R.drawable.notok_button_shape);

                    // The item's level is 5 by default. If it is not 5 i check the level to set
                    // the correct color to the button
                    if( item.getLevelNumber() != 5 ) {
                        if( item.getLevelNumber() == 0 ) {
                            holder.notOk.setBackgroundResource(R.drawable.red_notok);
                            //holder.notOk.setImageDrawable(getResources().getDrawable(R.drawable.zero));
                        } else if( item.getLevelNumber() == 1 ) {
                            holder.notOk.setBackgroundResource(R.drawable.orange_notok);
                            //holder.notOk.setImageDrawable(getResources().getDrawable(R.drawable.one));
                        } else if( item.getLevelNumber() == 2 ) {
                            holder.notOk.setBackgroundResource(R.drawable.yellow_notok);
                            //holder.notOk.setImageDrawable(getResources().getDrawable(R.drawable.two));
                        } else if( item.getLevelNumber() == 6 ) {
                            holder.notOk.setBackgroundResource(R.drawable.notok_button_shape);
                            //holder.notOk.setImageDrawable(getResources().getDrawable(R.drawable.two));
                        }
                    } else {
                        holder.notOk.setBackgroundResource(R.drawable.ok_cancel_button_shape);
                        //item.setLevelNumber(5);
                        //holder.notOk.setImageDrawable(getResources().getDrawable(R.drawable.cancel));
                    }
                }
            }
            // we will use getTag in the buttons listeners to manipulate the
            // right TestItem

            // for the ok/notok buttons i taged the other button for each because when
            // the user choose one of them then the other view changed as well
            ArrayList<Object> okTag = new ArrayList<Object>(3);
            okTag.add(item);
            okTag.add(holder.number);
            okTag.add(holder.notOk);
            okTag.add(holder.levelsBtnLayout);
            holder.ok.setTag(okTag);
            ArrayList<Object> notOkTag = new ArrayList(3);
            notOkTag.add(item);
            notOkTag.add(holder.number);
            notOkTag.add(holder.ok);
            notOkTag.add(holder.levelsBtnLayout);
            holder.notOk.setTag(notOkTag);
            holder.gallery.setTag(item);
            holder.image.setTag(item);
            holder.comment.setTag(item);
            holder.number.setTag(item);

            ArrayList<Object> levelTag = new ArrayList<Object>(2);
            levelTag.add(item);
            levelTag.add(holder.levelsBtnLayout);
            levelTag.add(holder.notOk);

            holder.title.setTag(item);
            holder.zero.setTag(levelTag);
            holder.one.setTag(levelTag);
            holder.two.setTag(levelTag);

            holder.levelsBtnLayout.setTag(item);

            return row;

        }

        /**
         * This class handles the clicking on the checkBox,
         * the image button, the info button and the comment button
         * @author Shira Elitzur
         *
         */
        private class ItemListener implements View.OnClickListener {

            @Override
            public void onClick(View view) {
                if( view.getId() == holder.ok.getId() ) {
                    setItemOk(view);
                } else if( view.getId() == holder.notOk.getId() ) {
                    setItemNotOk(view);
                } else if ( view.getId() == holder.image.getId()) {
                    camera(view);
                } else if ( view.getId() == holder.gallery.getId() ) {
                    gallery(view);
                } else if ( view.getId() == holder.title.getId() ) {
                    info(view);
                } else if ( view.getId() == holder.comment.getId() ) {
                    comment(view);
                } else if ( view.getId() == holder.zero.getId() ) {
                    zero(view);
                } else if ( view.getId() == holder.one.getId() ) {
                    one(view);
                } else if ( view.getId() == holder.two.getId() ) {
                    two(view);
                }

            }


            private void gallery( View view ) {
                TestItem item = (TestItem)view.getTag();

                if( (item.getImage1Code() != null || item.getImage2Code() != null) &&
                        !safety ) {
//				if( item.getImage1() != null || item.getImage2() != null ) {

                    // Dialog to show the captured image
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChapterActivity2.this);
                    LinearLayout layout = new LinearLayout(ChapterActivity2.this);
                    View dialogView = View.inflate(ChapterActivity2.this, R.layout.image_dialog_layout, null);

                    if ( item.getImage1Code() != null ) {
//					if ( item.getImage1() != null ) {
                        ImageView image1 = (ImageView)dialogView.findViewById(R.id.imageView1);
                        ImageButton delete1 = (ImageButton)dialogView.findViewById(R.id.delete1);
                        ArrayList<Object> tagsArray = new ArrayList<Object>(2);
                        tagsArray.add(item);
                        tagsArray.add(image1);
                        delete1.setTag(tagsArray);
                        //byte[] byteArray = item.getImage1();
                        byte[] byteArray = mySql.selectImage(item.getImage1Code());
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Config.RGB_565;
                        image1.setImageBitmap(BitmapFactory.decodeByteArray(
                                byteArray , 0, byteArray.length,options));
                        delete1.setOnClickListener( new OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                ArrayList<Object> tags = (ArrayList<Object>)view.getTag();
                                TestItem item = (TestItem)tags.get(0);
                                ImageView image = (ImageView)tags.get(1);
                                item.setImage1(null);
                                mySql.deleteImage(item.getImage1Code());
                                item.setImage1Code(null);
                                image.setImageBitmap(null);
                                image.setBackground(getResources().getDrawable(R.drawable.image_shape));
                            }
                        });
                    }

                    if( item.getImage2Code() != null ) {
                        //if ( item.getImage2() != null ) {
                        ImageView image2 = (ImageView)dialogView.findViewById(R.id.imageView2);
                        ImageButton delete2 = (ImageButton)dialogView.findViewById(R.id.delete2);
                        ArrayList<Object> tagsArray = new ArrayList<Object>(2);
                        tagsArray.add(item);
                        tagsArray.add(image2);
                        delete2.setTag(tagsArray);
                        //byte[] byteArray = item.getImage2();
                        byte[] byteArray = mySql.selectImage(item.getImage2Code());
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Config.RGB_565;
                        image2.setImageBitmap(BitmapFactory.decodeByteArray(
                                byteArray , 0, byteArray.length,options));
                        delete2.setOnClickListener( new OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                ArrayList<Object> tags = (ArrayList<Object>)view.getTag();
                                TestItem item = (TestItem)tags.get(0);
                                ImageView image = (ImageView)tags.get(1);
                                item.setImage2(null);
                                mySql.deleteImage(item.getImage2Code());
                                item.setImage2Code(null);
                                image.setImageBitmap(null);
                                image.setBackground(getResources().getDrawable(R.drawable.image_shape));

                            }
                        });
                    }


                    builder.setView(dialogView);
                    builder.create();
                    builder.show();
                } else {
                    String msg;
                    if( safety) {
                        msg = "לא זמין עבור אישורי בטיחות";
                    } else {
                        msg = getResources().getString(R.string.no_pics);
                    }
                    Toast.makeText(ChapterActivity2.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            /**
             * The priority of each item range between 0-2 when 0 is the most urgent.
             * When the user clicks the 'x' button three buttons appear on the 'x' button
             * and he should choose the priority. His choice affects the color of the button
             * and set the item's level as chosen.
             * @param view
             */
            private void zero( View view ) {
                ArrayList<Object> tagsList = (ArrayList<Object>)view.getTag();
                TestItem item = (TestItem)tagsList.get(0);
                LinearLayout levelBtnsLayout = (LinearLayout)tagsList.get(1);
                ImageButton notOk = (ImageButton)tagsList.get(2);
                item.setLevelNumber(0);
                item.setLevel(false);
                item.setOk(false);
                levelBtnsLayout.setVisibility(LinearLayout.GONE);
                notOk.setBackgroundResource(R.drawable.red_notok);
                //notOk.setImageDrawable(getResources().getDrawable(R.drawable.zero));
            }

            /**
             * The priority of each item range between 0-2 when 0 is the most urgent.
             * When the user clicks the 'x' button three buttons appear on the 'x' button
             * and he should choose the priority. His choice affects the color of the button
             * and set the item's level as chosen.
             * @param view
             */
            private void one( View view ) {
                ArrayList<Object> tagsList = (ArrayList<Object>)view.getTag();
                TestItem item = (TestItem)tagsList.get(0);
                LinearLayout levelBtnsLayout = (LinearLayout)tagsList.get(1);
                ImageButton notOk = (ImageButton)tagsList.get(2);
                item.setLevelNumber(1);
                item.setLevel(false);
                item.setOk(false);
                levelBtnsLayout.setVisibility(LinearLayout.GONE);
                notOk.setBackgroundResource(R.drawable.orange_notok);
                //notOk.setImageDrawable(getResources().getDrawable(R.drawable.one));
            }

            /**
             * The priority of each item range between 0-2 when 0 is the most urgent.
             * When the user clicks the 'x' button three buttons appear on the 'x' button
             * and he should choose the priority. His choice affects the color of the button
             * and set the item's level as chosen.
             * @param view
             */
            private void two( View view ) {
                ArrayList<Object> tagsList = (ArrayList<Object>)view.getTag();
                TestItem item = (TestItem)tagsList.get(0);
                LinearLayout levelBtnsLayout = (LinearLayout)tagsList.get(1);
                ImageButton notOk = (ImageButton)tagsList.get(2);
                item.setLevelNumber(2);
                item.setLevel(false);
                item.setOk(false);
                levelBtnsLayout.setVisibility(LinearLayout.GONE);
                notOk.setBackgroundResource(R.drawable.yellow_notok);
                //notOk.setImageDrawable(getResources().getDrawable(R.drawable.two));
            }

            private void setItemOk( View view ) {
                ArrayList<Object> list = (ArrayList<Object>)view.getTag();
                TestItem item = (TestItem)list.get(0);
                TextView number = (TextView)list.get(1);
                View notOk = (View)list.get(2);
                LinearLayout levelBtnsLayout = (LinearLayout)list.get(3);
                item.setOk(true);
                number.setTextColor(getResources().getColor(R.color.ok_text_color));
                view.setBackgroundResource(R.drawable.ok_button_shape);
                notOk.setBackgroundResource(R.drawable.ok_cancel_button_shape);
                item.setLevel(false);
                levelBtnsLayout.setVisibility(LinearLayout.GONE);


            }

            private void setItemNotOk( View view ) {
                ArrayList<Object> list = (ArrayList<Object>)view.getTag();
                TestItem item = (TestItem)list.get(0);
                TextView number = (TextView)list.get(1);
                View ok = (View)list.get(2);
                LinearLayout levelBtnsLayout = (LinearLayout)list.get(3);
                item.setFirst(true);
                number.setTextColor(getResources().getColor(R.color.notok_text_color));
                view.setBackgroundResource(R.drawable.notok_button_shape);
                ok.setBackgroundResource(R.drawable.ok_cancel_button_shape);
                if(safety) {
                    item.setOk(false);
                    item.setLevelNumber(6);
                    return;
                }
                //item.setLevel(true);
                item.setLevelNumber(5);
                levelBtnsLayout.setVisibility(LinearLayout.VISIBLE);

            }

            private void comment( View view ) {
                TestItem item = (TestItem) view.getTag();
                globalItem = item;
                Log.e("testItem", "onClick comment for: " + item.getTemplateItem().getTitle());
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(ChapterActivity2.this);

                LinearLayout layout = new LinearLayout(ChapterActivity2.this);
                commentEditText = new EditText(ChapterActivity2.this);
                LayoutParams lparams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
                commentEditText.setLayoutParams(lparams);

                if( item.getCommnet() != null ) {
                    Log.e("testItem", "if there a prev value: " + item.getTemplateItem().getTitle());
                    commentEditText.setText(item.getCommnet());
                }

                layout.addView(commentEditText);
                // add the custom layout as the dialog view
                builder.setView(layout);
                builder.setPositiveButton("שמור", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("testItem", "press save for: " + globalItem.getTemplateItem().getTitle());
                        globalItem.setCommnet(commentEditText.getText().toString());


                    }
                });
                dialog = builder.create();
                //dialog.show();
                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                dialog.show();
                //builder.show();
            }
//			private void check( View view ) {
//				CheckBox cb = (CheckBox)view;
//				TestItem test = (TestItem)cb.getTag();
//				test.setOk( cb.isChecked());
//			}

            private void camera( View view ) {
                TestItem item = (TestItem) view.getTag();
                if( (item.getImage1Code() == null || item.getImage2Code() == null) &&
                        !safety ) {

                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File img = null;
                    try {
                        img = File.createTempFile("temp", ".jpg", Environment.getExternalStorageDirectory());
                        Log.e("delete file", "file delete: " + img.delete());
                    } catch (IOException e) {
                        Log.e("debug", "IOException- failed to create temporary file");
                        e.printStackTrace();
                    }

                    imgUri = Uri.fromFile(img);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                    if( item.getImage1Code() == null ) {
                        imageNumber = 1;
                    } else if( item.getImage2Code() == null ) {
                        imageNumber = 2;
                    } else {
                        return;
                    }
                    globalItem = item;
                    //cameraIntent.putExtra("item", item);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                } else {
                    if(safety) {
                        Toast.makeText(ChapterActivity2.this, "לא זמינה מצלמה עבור אישורי בטיחות", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChapterActivity2.this, getResources().getString(R.string.no_more_pics), Toast.LENGTH_SHORT).show();
                    }
                }

            }


            private void info( View view ) {
                TestItem item = (TestItem) view.getTag();
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(ChapterActivity2.this);

                LinearLayout layout = new LinearLayout(ChapterActivity2.this);
                layout.setBackgroundResource(R.drawable.info_dialog_bg);
                TextView info = new TextView(ChapterActivity2.this);
                //info.setGravity(Gravity.CENTER);
                if( item.getTemplateItem().getDescription() != null ) {
                    info.setText(item.getTemplateItem().getDescription());
                } else {
                    info.setText(getResources().getString(R.string.no_description));
                }

                info.setTextSize(25);
                layout.addView(info);
                // add the custom layout as the dialog view
                builder.setView(layout);
                dialog = builder.create();

                dialog.show();
            }


        }

        /**
         * Holder holds the views of any row in the listView.
         * When i use holder i don't need to use findViewById any time the row
         * refresh itself (a lot of times) and someone somewhere said that
         * findViewById is expensive.
         * @author Shira Elitzur
         *
         */
        private class ViewHolder {
            TextView title;
            TextView number;
            CheckBox check;
            ImageButton ok;
            ImageButton notOk;
            ImageButton gallery;
            ImageButton image;
            ImageButton comment;
            LinearLayout levelsBtnLayout;
            Button zero;
            Button one;
            Button two;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK ) {

            Bitmap bitmap = null;

            byte[] byteArray = null;
            // get the bitmap from the uri that it was stored in

            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            File file = new File( imgUri.getPath());
            file.delete();

            //TestItem item = (TestItem) data.getSerializableExtra("item");
            // set the image to the item instance


            ByteArrayOutputStream stream = new ByteArrayOutputStream();


            if( bitmap != null ) {
                // on the tablet i will look for the right quality that the memory
                // can handle with.
                // ! must reduce the quality or the app will crash even when i keep
                // the images on the device's memory.
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byteArray = stream.toByteArray();
            }

            if( imageNumber == 1 ) {
                globalItem.setImage1(byteArray);
            } else if( imageNumber == 2 ) {
                globalItem.setImage2(byteArray);
            }

//			CustomAsyncTask saveImageTask = new CustomAsyncTask(
//					this, CustomAsyncTask.SAVE_IMAGE_T);
//			saveImageTask.execute();

            String title = SessionGlobalData.getInstance().getInstitutionTilte();
            String date = SessionGlobalData.getInstance().getTestDate();
            // image uniqe code that is build from: title.date.itemNumber.imageNumber
            // example: morasha.21.12.15.1.5.2
            String code = title + "." + date + "." +globalItem.getTemplateItem().getItemNumber()+"."+imageNumber;

            // I keep the byte[] of the image on the sqlite database couse the application's
            // memory can't handle more than few images
            if ( imageNumber == 1 ) {
                globalItem.setImage1Code( code );
                mySql.insertImage(globalItem.getImage1(), code);
                Log.e("", "Image 1 code to save : " + code);
                globalItem.setImage1(null);
            } else if ( imageNumber == 2 ) {
                globalItem.setImage2Code( code );
                mySql.insertImage(globalItem.getImage2(), code);
                Log.e("", "Image 2 code to save : " + code);
                globalItem.setImage2(null);
            }



        }
    }


    @Override
    public void beforeTask() {
        // TODO Auto-generated method stub

    }


    @Override
    public void onTaskCompleted(boolean result, Object ob, int taskId) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onProgress(int taskId, int prog) {
        // TODO Auto-generated method stub

    }


    @Override
    public boolean task(int taskId) {

        if ( taskId == CustomAsyncTask.SAVE_IMAGE_T ) {
            String title = SessionGlobalData.getInstance().getInstitutionTilte();
            String date = SessionGlobalData.getInstance().getTestDate();
            // image uniqe code that is build from: title.date.itemNumber.imageNumber
            // example: morasha.21.12.15.1.5.2
            String code = title + "." + date + "." +globalItem.getTemplateItem().getItemNumber()+"."+imageNumber;
            if ( imageNumber == 1 ) {
                globalItem.setImage1Code( code );
                mySql.insertImage(globalItem.getImage1(), code);
                Log.e("", "Image 1 code to save : " + code);
                globalItem.setImage1(null);
            } else if ( imageNumber == 2 ) {
                globalItem.setImage2Code( code );
                mySql.insertImage(globalItem.getImage2(), code);
                Log.e("", "Image 2 code to save : " + code);
                globalItem.setImage2(null);
            }
        }
        return false;
    }


}
