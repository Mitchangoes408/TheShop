## Notes
---

### 3/9
**Current Status**
* MainActivity is the user profile
* UserProfile displays:
  - ImageView: could be most recent/favorite cut
  - TextView: could be description of selected ImageView
  - Appointment Section:
  - RecyclerView of "cuts"
    - displays UUID
    - Grid of 3 cols

**Functionalities**
* Add cut through button press
* View cut image on press
  - swipe through to next cut image

**Thoughts on To-Dos**
[ ] Add a way to add appointments
[ ] Way to prioritize image for profile(whether using most recent image or favorite cut)
[ ] ***MAYBE*** RecyclerView inside RecyclerView in case a cut has several images for angles/profiles
[ ] Get images to work for profile RecyclerView
  - Set to default image is cut is made witout taking image
  - If default, add way to add image later
[ ] Add feedback/notes for appointments
  - Comment field
  - Digital comment box

---

### 3/10
**Today's Goals**
[x] Adding appointments and displaying on Appointments view
[x] Add RecyclerView to UserProfile
[x] Connect "New Appointment" menu option to bring up way to schedule appointment

---

### 3/11

**Progress**
- Profile is updated w/ Appt RecyclerView
- New appointments can be added through the menu option
- Cuts and Appointments are on seperate databases: theshop_cuts.db and theshop_appt.db
  - It will remain like this until DBHelper class can smoothly be used for multiple tables
- Appts display on RecyclerView but:
  - Displays default date: Wed Dec 31 1969
  - Does not automatically add new appt... Needs a refresh

**Today's Goals***
[ ] Properly set and display appointments' date/time
[ ] Automatically add and refresh Appt RecyclerView
[ ] Get camera widget working

---

### 3/15

**Progress**
- New appointment button brings up a scheduling form with:
  * Cut type: can select from "Buzz cut" or "Scissor cut"
  * Date button that pulls up a date picker

**To-Dos**
[x] Get RecyclerView to refresh with date from date picker
[x] Add onClickListener for appointment view
  [x] Should bring up "Cancel" or "Complete"
  [x] On "Cancel", delete from DB
  [x] On "Complete", start camera widget to take pictures of cut then add it to profile
    [x] Get camera widget to work

---

### 3/16

**Progress**
- Date form button changes to selected date from date picker
- Appt cancel button works to remove from DB and refresh RecyclerView

**To-Dos**
[x] Mode "+" button to "Complete Appt" to add to DB and start widget
[x] Get photo storage location to work
[x] Get "Schedule Appt" button to work or remove
  - Toast is made
  [x] Make go back to main MainActivity
[x] Add the appointment/cut type to the appt details
[ ] Add time picker after date picker
[x] Pull the appointment details into cut description
[x] Use the onClickListener to blow up the desired image
[x] find a way to "favorite" a cut to display

---

### 4/25

**Progress**
- App starts on a login screen
- Database stores login information
- Images can be favorites and displayed with their details
- Simple sign out button added

**To-Dos**
[x] Adjust home screen to be dependent on account type
[ ] Get "Forgot Username/Password" buttons working
[x] Long press image to delete
[ ] Properly track scheduled appointments
*Once Barber Homepage is done..*
  [ ] bring online with client server communications
  [ ] Connect to Square for payment gateway
  [ ]connect to MySQL for database storage on server side 

**Other Notes**
* Thoughts: (Barber side functionality)
  [ ] Barber account only needs to display appointments
  [ ] Main priority is a payment system figured out

**End of Day Progress**
- Users account type is now entered into DB as "Barber" or "Client"
  - Depending on acct type, the home screen display is different
