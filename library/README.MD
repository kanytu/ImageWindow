# ImageWindow
An image with a close button like Google Messenger attachments.


**Usage**

 Add ImageWindow to your xml:

     <com.poliveira.apps.imagewindow.ImageWindow
         android:layout_width="350dp"
         android:layout_height="wrap_content"
         android:id="@+id/myImageView"/>

XML attributes:

    app:close_color - color of the close button (default = 0xffff7b57)
    app:close_margin - margin for the close button (default = 5dp)
    app:close_size - size of the close button (default = 32dp)
    app:corner_radius - corner radius for the ImageView (default = 7dp)
    app:image_margin - top and left margin of the ImageView (default = 10dp)
    app:close_icon - close drawable (default = ic_action_close)

Set an image to it on your activity:

    ImageWindow myImageWindow = (ImageWindow) findViewById(R.id.myImageView);
    myImageWindow.getImageView().setImageResource(R.drawable.myCat);

Set a delete callback for when you click on the close button

    myImageWindow.setOnCloseListener(new ImageWindow.OnCloseListener() {
            @Override
            public void onCloseClick(final View imageWindow) {
                imageWindow.animate().alpha(0).setDuration(500).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ((ViewGroup) (imageWindow.getParent())).removeView(imageWindow);
                    }
                }).start();
            }
    });


**RESULT**

![ImageWindow](https://raw.githubusercontent.com/kanytu/ImageWindow/master/master/screenshots/device-2015-02-20-162825.png)

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-ImageWindow-red.svg?style=flat)](http://android-arsenal.com/details/1/1576)