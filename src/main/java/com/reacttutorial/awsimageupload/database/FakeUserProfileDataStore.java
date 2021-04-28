package com.reacttutorial.awsimageupload.database;

import com.reacttutorial.awsimageupload.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {
    private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

    static{
        USER_PROFILES.add(new UserProfile(UUID.fromString("9196bb58-2f7f-4058-8368-4762dc130123"), "ShelbyPace", null));
        USER_PROFILES.add(new UserProfile(UUID.fromString("0fc4cf67-3718-45aa-981d-0c3c0fdb5ad8"), "FlynnRider", null));
    }


    public List<UserProfile> getUserProfiles() {
        return USER_PROFILES;
    }
}
