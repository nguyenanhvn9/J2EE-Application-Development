package com.example.demo.dto;

import lombok.Generated;

class CompanyDTO {
   private String name;
   private String catchPhrase;
   private String bs;

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getCatchPhrase() {
      return this.catchPhrase;
   }

   @Generated
   public String getBs() {
      return this.bs;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setCatchPhrase(final String catchPhrase) {
      this.catchPhrase = catchPhrase;
   }

   @Generated
   public void setBs(final String bs) {
      this.bs = bs;
   }

   @Generated
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof CompanyDTO)) {
         return false;
      } else {
         CompanyDTO other = (CompanyDTO)o;
         if (!other.canEqual(this)) {
            return false;
         } else {
            label47: {
               Object this$name = this.getName();
               Object other$name = other.getName();
               if (this$name == null) {
                  if (other$name == null) {
                     break label47;
                  }
               } else if (this$name.equals(other$name)) {
                  break label47;
               }

               return false;
            }

            Object this$catchPhrase = this.getCatchPhrase();
            Object other$catchPhrase = other.getCatchPhrase();
            if (this$catchPhrase == null) {
               if (other$catchPhrase != null) {
                  return false;
               }
            } else if (!this$catchPhrase.equals(other$catchPhrase)) {
               return false;
            }

            Object this$bs = this.getBs();
            Object other$bs = other.getBs();
            if (this$bs == null) {
               if (other$bs != null) {
                  return false;
               }
            } else if (!this$bs.equals(other$bs)) {
               return false;
            }

            return true;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof CompanyDTO;
   }

   @Generated
   public int hashCode() {
      boolean PRIME = true;
      int result = 1;
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $catchPhrase = this.getCatchPhrase();
      result = result * 59 + ($catchPhrase == null ? 43 : $catchPhrase.hashCode());
      Object $bs = this.getBs();
      result = result * 59 + ($bs == null ? 43 : $bs.hashCode());
      return result;
   }

   @Generated
   public String toString() {
      String var10000 = this.getName();
      return "CompanyDTO(name=" + var10000 + ", catchPhrase=" + this.getCatchPhrase() + ", bs=" + this.getBs() + ")";
   }

   @Generated
   public CompanyDTO() {
   }
}
