package com.example.demo.dto;

import lombok.Generated;

public class AddressDTO {
   private String street;
   private String suite;
   private String city;
   private String zipcode;
   private GeoDTO geo;

   @Generated
   public String getStreet() {
      return this.street;
   }

   @Generated
   public String getSuite() {
      return this.suite;
   }

   @Generated
   public String getCity() {
      return this.city;
   }

   @Generated
   public String getZipcode() {
      return this.zipcode;
   }

   @Generated
   public GeoDTO getGeo() {
      return this.geo;
   }

   @Generated
   public void setStreet(final String street) {
      this.street = street;
   }

   @Generated
   public void setSuite(final String suite) {
      this.suite = suite;
   }

   @Generated
   public void setCity(final String city) {
      this.city = city;
   }

   @Generated
   public void setZipcode(final String zipcode) {
      this.zipcode = zipcode;
   }

   @Generated
   public void setGeo(final GeoDTO geo) {
      this.geo = geo;
   }

   @Generated
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof AddressDTO)) {
         return false;
      } else {
         AddressDTO other = (AddressDTO)o;
         if (!other.canEqual(this)) {
            return false;
         } else {
            label71: {
               Object this$street = this.getStreet();
               Object other$street = other.getStreet();
               if (this$street == null) {
                  if (other$street == null) {
                     break label71;
                  }
               } else if (this$street.equals(other$street)) {
                  break label71;
               }

               return false;
            }

            Object this$suite = this.getSuite();
            Object other$suite = other.getSuite();
            if (this$suite == null) {
               if (other$suite != null) {
                  return false;
               }
            } else if (!this$suite.equals(other$suite)) {
               return false;
            }

            label57: {
               Object this$city = this.getCity();
               Object other$city = other.getCity();
               if (this$city == null) {
                  if (other$city == null) {
                     break label57;
                  }
               } else if (this$city.equals(other$city)) {
                  break label57;
               }

               return false;
            }

            Object this$zipcode = this.getZipcode();
            Object other$zipcode = other.getZipcode();
            if (this$zipcode == null) {
               if (other$zipcode != null) {
                  return false;
               }
            } else if (!this$zipcode.equals(other$zipcode)) {
               return false;
            }

            Object this$geo = this.getGeo();
            Object other$geo = other.getGeo();
            if (this$geo == null) {
               if (other$geo == null) {
                  return true;
               }
            } else if (this$geo.equals(other$geo)) {
               return true;
            }

            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof AddressDTO;
   }

   @Generated
   public int hashCode() {
      boolean PRIME = true;
      int result = 1;
      Object $street = this.getStreet();
      result = result * 59 + ($street == null ? 43 : $street.hashCode());
      Object $suite = this.getSuite();
      result = result * 59 + ($suite == null ? 43 : $suite.hashCode());
      Object $city = this.getCity();
      result = result * 59 + ($city == null ? 43 : $city.hashCode());
      Object $zipcode = this.getZipcode();
      result = result * 59 + ($zipcode == null ? 43 : $zipcode.hashCode());
      Object $geo = this.getGeo();
      result = result * 59 + ($geo == null ? 43 : $geo.hashCode());
      return result;
   }

   @Generated
   public String toString() {
      String var10000 = this.getStreet();
      return "AddressDTO(street=" + var10000 + ", suite=" + this.getSuite() + ", city=" + this.getCity() + ", zipcode=" + this.getZipcode() + ", geo=" + String.valueOf(this.getGeo()) + ")";
   }

   @Generated
   public AddressDTO() {
   }
}
