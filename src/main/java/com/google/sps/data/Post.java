package com.google.sps.data;
import com.google.cloud.datastore.Blob;

public final class Post {

  private final String PET_TYPE;
  private final String BREED;
  private final String AGE;
  private final String LOCATION;
  private final String NAME;
  private final String EMAIL;
  private final String PHONE;
  private final String PIC;

  public Post(String petType, String breed, String age, String location, String name, String email, String phone, String pic) {
    this.PET_TYPE = petType;
    this.BREED = breed;
    this.AGE = age;
    this.LOCATION = location;
    this.NAME = name;
    this.EMAIL = email;
    this.PHONE = phone;
    this.PIC = pic;
  }
}