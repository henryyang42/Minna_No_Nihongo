This is an Android Japanese learning app. 

It contains 50 lessons of みんなの日本語.

At first I use PDFViewer sdk to display the pdf lessons. However, it turned out that the sdk seem to have some problem parsing Japanese and Chinses.
Therefore, I use WebView instead and turn all PDFs into htmls so that I can read it from WebView.

I also impliment a mediacontroller to enable user to listen to the lesson recording.

This is considered as a practice to get famillar some Android's API and concepts.

Things to be improved:
1. UI design
2. To reduce the the .apk size (too much audio)
