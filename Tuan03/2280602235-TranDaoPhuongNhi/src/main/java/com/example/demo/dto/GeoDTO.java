package com.example.demo.dto;

import lombok.Generated;

class GeoDTO {
   private String lat;
   private String lng;

   @Generated
   public String getLat() {
      return this.lat;
   }

   @Generated
   public String getLng() {
      return this.lng;
   }

   @Generated
   public void setLat(final String lat) {
      this.lat = lat;
   }

   @Generated
   public void setLng(final String lng) {
      this.lng = lng;
   }

   @Generated
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof GeoDTO)) {
         return false;
      } else {
         GeoDTO other = (GeoDTO)o;
         if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$lat = this.getLat();
            Object other$lat = other.getLat();
            if (this$lat == null) {
               if (other$lat != null) {
                  return false;
               }
            } else if (!this$lat.equals(other$lat)) {
               return false;
            }

            Object this$lng = this.getLng();
            Object other$lng = other.getLng();
            if (this$lng == null) {
               if (other$lng != null) {
                  return false;
               }
            } else if (!this$lng.equals(other$lng)) {
               return false;
            }

            return true;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof GeoDTO;
   }

   @Generated
   public int hashCode() {
      boolean PRIME = true;
      int result = 1;
      Object $lat = this.getLat();
      result = result * 59 + ($lat == null ? 43 : $lat.hashCode());
      Object $lng = this.getLng();
      result = result * 59 + ($lng == null ? 43 : $lng.hashCode());
      return result;
   }

   @Generated
   public String toString() {
      String var10000 = this.getLat();
      return "GeoDTO(lat=" + var10000 + ", lng=" + this.getLng() + ")";
   }

   @Generated
   public GeoDTO() {
   }
}
