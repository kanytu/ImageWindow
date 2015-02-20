# ImageWindow
An image with a close button like Google Messenger attachments.


**Usage**

 Add ImageWindow to your xml:

     <com.poliveira.apps.imagewindow.ImageWindow
         android:layout_width="350dp"
         android:layout_height="wrap_content"
         android:id="@+id/myImageView"/>

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

![ImageWindow](https://raw.githubusercontent.com/kanytu/ImageWindow/master/screenshots/device-2015-02-20-162825.png)
