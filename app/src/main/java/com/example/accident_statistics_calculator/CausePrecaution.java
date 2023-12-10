package com.example.accident_statistics_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class CausePrecaution extends AppCompatActivity {

    private String selectedCause, selectedCity, selectedDistrict, selectedState;
    TextView area_tv, cause_tv, precaution_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cause_precaution);

        selectedCause = getIntent().getStringExtra("selectedCause");
        selectedCity = getIntent().getStringExtra("selectedCity");
        selectedDistrict = getIntent().getStringExtra("selectedDistrict");
        selectedState = getIntent().getStringExtra("selectedState");

        area_tv = (TextView) findViewById(R.id.area_tv);
        cause_tv = (TextView) findViewById(R.id.cause_tv);
        precaution_tv = (TextView) findViewById(R.id.precaution_tv);

        area_tv.setText(selectedCity + ", " + selectedDistrict + ", " + selectedState);
        cause_tv.setText("Cause Of Accidents: " + selectedCause);
        if(selectedCause.equals("Distracted driving")){
            precaution_tv.setText("1) Avoid using your phone while driving: Using your phone while driving is one of the most common causes of distracted driving. To avoid this, you should turn off your phone or put it on silent mode while driving. If you need to use your phone, pull over to a safe location and then use it.\n\n2) Avoid eating or drinking while driving: Eating or drinking while driving can also be a major distraction. To avoid this, you should finish your meal or drink before getting behind the wheel.\n\n3) Avoid multitasking while driving: Multitasking while driving, such as applying makeup or adjusting the radio, can also be a major distraction. To avoid this, you should focus on driving and avoid any other activities that can take your attention away from the road.\n\n4) Keep your eyes on the road: It's important to keep your eyes on the road at all times while driving. Avoid looking at anything that can distract you, such as billboards or other vehicles.\n\n5) Take breaks: If you're feeling tired or drowsy, take a break and rest before continuing your journey. Fatigue can also be a major cause of distracted driving.");

        } else if (selectedCause.equals("Speeding")) {
            precaution_tv.setText("1) Observe speed limits: Always observe the posted speed limits on the road. Speed limits are set based on the road conditions and the surrounding environment, and exceeding them can increase the risk of accidents.\n\n2) Adjust your speed to the road conditions: Adjust your speed based on the road conditions, such as weather, traffic, and visibility. Slow down if the road is wet or slippery, or if there is heavy traffic or poor visibility.\n\n3) Maintain a safe following distance: Maintain a safe following distance from the vehicle in front of you. This will give you enough time to react if the vehicle suddenly stops or slows down.\n\n4) Avoid aggressive driving: Avoid aggressive driving behaviors such as tailgating, weaving in and out of traffic, and cutting off other drivers. These behaviors can increase the risk of accidents and road rage incidents.\n\n5) Use your seatbelt: Always wear your seatbelt while driving. Seatbelts can help protect you in the event of an accident, and can reduce the severity of injuries.\n\n6) Avoid distractions: Avoid any distractions while driving, such as using your phone, eating, or adjusting the radio. These distractions can take your attention away from the road and increase the risk of accidents.");

        } else if (selectedCause.equals("Driving under the influence")) {
            precaution_tv.setText("1) Don't drink and drive: If you plan to drink, make sure you have a designated driver or use a ride-sharing service to get home safely. Never get behind the wheel after drinking alcohol.\n\n2) Don't use drugs and drive: Never use drugs, including prescription drugs, that can impair your ability to drive safely. If you're taking medication that can cause drowsiness or other side effects, talk to your doctor about alternative options.\n\n3) Plan ahead: If you know you'll be drinking or using drugs, plan ahead and make arrangements for a safe ride home. This can include using a designated driver, a ride-sharing service, or public transportation.\n\n4) Use seatbelts: Always wear your seatbelt while driving. Seatbelts can help protect you in the event of an accident, and can reduce the severity of injuries.\n\n5) Report suspected drunk drivers: If you see a driver who you suspect is under the influence of drugs or alcohol, report it to the authorities immediately. This can help prevent accidents and save lives.\n\n6) Educate others: Educate your friends and family about the dangers of driving under the influence and encourage them to avoid it. Offer to be a designated driver for others who may need a ride home.");

        } else if (selectedCause.equals("Reckless driving")) {
            precaution_tv.setText("1) Follow traffic rules: Always follow traffic rules and regulations, including speed limits, traffic signals, and road signs. These rules are designed to keep you and other drivers safe on the road.\n\n2) Avoid aggressive driving: Avoid aggressive driving behaviors such as tailgating, weaving in and out of traffic, and cutting off other drivers. These behaviors can increase the risk of accidents and road rage incidents.\n\n3) Stay focused: Stay focused on the road and avoid any distractions while driving, such as using your phone, eating, or adjusting the radio. These distractions can take your attention away from the road and increase the risk of accidents.\n\n4) Use your seatbelt: Always wear your seatbelt while driving. Seatbelts can help protect you in the event of an accident, and can reduce the severity of injuries.\n\n5) Maintain your vehicle: Regularly maintain your vehicle, including checking the brakes, tires, and lights. A well-maintained vehicle is less likely to break down or cause accidents.\n\n6) Be aware of your surroundings: Be aware of your surroundings and anticipate the actions of other drivers. This can help you avoid accidents and react quickly to any unexpected situations.");

        } else if (selectedCause.equals("Poor road conditions")) {
            precaution_tv.setText("1) Report poor road conditions: If you encounter poor road conditions, such as potholes or debris on the road, report them to the authorities immediately. This can help prevent accidents and ensure that the road is repaired or cleared as soon as possible.\n\n2) Observe speed limits: Always observe the posted speed limits on the road. Speed limits are set based on the road conditions and the surrounding environment, and exceeding them can increase the risk of accidents.\n\n3) Adjust your speed to the road conditions: Adjust your speed based on the road conditions, such as weather, traffic, and visibility. Slow down if the road is wet or slippery, or if there is heavy traffic or poor visibility.\n\n4) Maintain a safe following distance: Maintain a safe following distance from the vehicle in front of you. This will give you enough time to react if the vehicle suddenly stops or slows down.\n\n5) Avoid sudden maneuvers: Avoid sudden maneuvers, such as sudden braking or swerving, on poor road conditions. These maneuvers can cause your vehicle to lose traction and increase the risk of accidents.");

        } else if (selectedCause.equals("Vehicle malfunctions")) {
            precaution_tv.setText("1) Regular maintenance: Regularly maintain your vehicle, including checking the brakes, tires, and lights. A well-maintained vehicle is less likely to break down or cause accidents.\n\n2) Check your vehicle before driving: Before driving, check your vehicle for any signs of damage or malfunctions. This can include checking the tires, brakes, lights, and fluid levels.\n\n3) Address any issues immediately: If you notice any issues with your vehicle, such as strange noises or warning lights, address them immediately. Ignoring these issues can lead to more serious problems and increase the risk of accidents.\n\n4) Use quality parts: Use quality parts when repairing or replacing parts of your vehicle. Cheap or low-quality parts can fail more easily and increase the risk of accidents.\n\n5) Follow manufacturer recommendations: Follow the manufacturer's recommendations for maintenance and repairs. This can help ensure that your vehicle is in good condition and reduce the risk of accidents.");

        } else if (selectedCause.equals("Weather conditions")) {
            precaution_tv.setText("1) Check weather conditions: Check the weather forecast before you start your journey. If there are severe weather conditions, such as heavy rain, snow, or ice, consider postponing your trip or taking an alternative route.\n\n2) Adjust your speed: Adjust your speed based on the weather conditions. Slow down if the road is wet or slippery, or if there is heavy rain, snow, or ice.\n\n3) Maintain a safe following distance: Maintain a safe following distance from the vehicle in front of you. This will give you enough time to react if the vehicle suddenly stops or slows down.\n\n4) Use your headlights: Use your headlights in poor visibility conditions, such as rain, fog, or snow. This will help you see the road ahead and make it easier for other drivers to see you.\n\n5) Use winter tires: Use winter tires in snowy or icy conditions. Winter tires provide better traction and handling in these conditions, reducing the risk of accidents.\n\n6) Avoid sudden maneuvers: Avoid sudden maneuvers, such as sudden braking or swerving, on wet or slippery roads. These maneuvers can cause your vehicle to lose traction and increase the risk of accidents.\n\n7) Be aware of your surroundings: Be aware of your surroundings and anticipate the actions of other drivers. This can help you avoid accidents and react quickly to any unexpected situations.");

        } else if (selectedCause.equals("Inexperienced drivers")) {
            precaution_tv.setText("1) Get proper training: Before getting behind the wheel, make sure you have received proper training and have a good understanding of the rules of the road. This can include taking a driver's education course or practicing with a licensed driver.\n\n2) Follow traffic rules: Always follow traffic rules and regulations, including speed limits, traffic signals, and road signs. These rules are designed to keep you and other drivers safe on the road.\n\n3) Avoid distractions: Avoid any distractions while driving, such as using your phone, eating, or adjusting the radio. These distractions can take your attention away from the road and increase the risk of accidents.\n\n4) Use your seatbelt: Always wear your seatbelt while driving. Seatbelts can help protect you in the event of an accident, and can reduce the severity of injuries.\n\n5) Avoid driving in adverse conditions: Avoid driving in adverse conditions, such as heavy rain, snow, or fog, until you have gained more experience and confidence on the road.\n\n6) Practice defensive driving: Practice defensive driving techniques, such as scanning the road ahead, anticipating the actions of other drivers, and maintaining a safe following distance.\n\n7) Gradually increase driving time: Gradually increase your driving time and experience, starting with short trips and gradually building up to longer drives. This can help you gain confidence and experience on the road.");

        } else if (selectedCause.equals("Failure to follow traffic rules")) {
            precaution_tv.setText("1) Know the rules: Familiarize yourself with the traffic rules and regulations in your area. This includes speed limits, traffic signals, and road signs.\n\n2) Observe speed limits: Always observe the posted speed limits on the road. Speed limits are set based on the road conditions and the surrounding environment, and exceeding them can increase the risk of accidents.\n\n3) Use turn signals: Use your turn signals when changing lanes or turning. This will help other drivers anticipate your actions and reduce the risk of accidents.\n\n4) Stop at stop signs and red lights: Always stop at stop signs and red lights. Running a red light or stop sign can cause serious accidents and injuries.\n\n5) Yield to pedestrians: Yield to pedestrians in crosswalks and other designated areas. Pedestrians have the right of way in these areas, and failing to yield to them can cause accidents and injuries.\n\n6) Avoid distracted driving: Avoid any distractions while driving, such as using your phone, eating, or adjusting the radio. These distractions can take your attention away from the road and increase the risk of accidents.\n\n7) Use your seatbelt: Always wear your seatbelt while driving. Seatbelts can help protect you in the event of an accident, and can reduce the severity of injuries.");

        } else if (selectedCause.equals("Unknown")) {
            Intent intent = new Intent(CausePrecaution.this, AdviceActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Here are some common causes and their precautions", Toast.LENGTH_LONG).show();

        }


    }
}