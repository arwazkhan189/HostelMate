# HostelMate
HostelMate - A Need For Hosteler (An Android App that helps students to find the best resource like hostels and tiffin centers etc.)

<b> Problem Statement: </b>

Students from various cities take admission to a college. Most of them suffer to find necessities like finding a good hostel and tiffin service. And even if they do find the resources, it is very hard to find reliable reviews and feedbacks in an unfamiliar city. At the same time, the students who are living in the city for a long time and the local people, know a lot about the available resources. We aim to build an Android Application that bridges this gap and solves this problem.

<b> Proposed Solution : </b>

HostelMate is an Android Application that collects data manually from the students and local people and helps the students to find a decent place to stay and good quality food service according to their requirements. 

Once the user logins and authenticates their details, they can browse for the Tiffin Centers, Hostels/PGs in their nearby location. The results include the prices, reviews, and ratings, location, contact details, and timings of the Facility. They can even Add a new Tiffin Center's, Hostel's details. The app includes the HostelMate Zone section where the user can find the FAQs, post a new Query, or post any suggestion for the betterment of the app.

<p align="center">
  <img  height='300' src="https://github.com/arwazkhan189/HostelMate/blob/master/screenshots/splashscreen.png">
  <img  height='300' src="https://github.com/arwazkhan189/HostelMate/blob/master/screenshots/login.png"> 
  <img  height='300' src="https://github.com/arwazkhan189/HostelMate/blob/master/screenshots/home.png">
  <img  height='300' src="https://github.com/arwazkhan189/HostelMate/blob/master/screenshots/hostel.png">
  <img  height='300' src="https://github.com/arwazkhan189/HostelMate/blob/master/screenshots/hosteldetails.png">
  <img  height='300' src="https://github.com/arwazkhan189/HostelMate/blob/master/screenshots/addhostel.png">
</p>
<p align="center">
<img  height='300' src="https://github.com/arwazkhan189/HostelMate/blob/master/screenshots/tiffin.png"> 
<img  height='300' src="https://github.com/arwazkhan189/HostelMate/blob/master/screenshots/tiffindetails.png">
<img  height='300' src="https://github.com/arwazkhan189/HostelMate/blob/master/screenshots/addtiffin.png">
<img  height='300' src="https://github.com/arwazkhan189/HostelMate/blob/master/screenshots/hostelmatezone.png">
<img  height='300' src="https://github.com/arwazkhan189/HostelMate/blob/master/screenshots/safety.png">
</p>

    	  	
<b> Functionality & Concepts used : </b>

- The App has a very simple and interactive interface that helps the user to find the best hostels and tiffin centers near the GEC Bilaspur. Following are a few android concepts used to achieve the functionalities in the app : 
- Layout: Most of the activities in the app uses a relative layout, linear layout, frame layout, card view, gridview, etc.
- Firebase Authentication: We used Firebase Authentication Using Google Sign-In which is a simple and fast method for authentication.
- Firebase Firestore: To store hostels and tiffin center details etc.
- Firebase storage: To store images related to hostels and the app etc.
- Firebase Realtime Database:  For Feedback section.
- RecyclerView: To display the list of hostels and tiffin centers present in that location.
- WebView & Google Form: To collect the data (Hostels and Tiffin Center) from the user of our app.
- SharedPreference: To Store the mobile number for safety Service (Currently not working).
- ViewBinding: it creates direct references to views
- Data Class: For Hostel and Tiffin Center Model
- Picasso Library: For image loading from the internet
- android-gif-drawable: For rendering gif present at the login page
- Other Tools used: Canva for logo design and Whimsical for wireframing and flowchart design.[Click here](https://github.com/arwazkhan189/HostelMate/tree/master/WirFramingAndFlowchart)

<b> Application Link & Future Scope : </b>

The app is currently in the Alpha testing phase with GEC Bilaspur with a limited no. of users, You can access the app : [Click here](https://github.com/arwazkhan189/HostelMate/raw/master/app/release/app-release.apk)

For now, the app covers a small part of the city, we plan to expand the App's coverage area by adding more and more relevant Service Providers i.e., Tiffin Centers and Hostels. 

We are also planning to add more features in the app like the Safety Service feature,  where the user can add the emergency contacts and send a message with their location, and call them in case of any crisis by performing a trigger action like a button press or phone shaking.

