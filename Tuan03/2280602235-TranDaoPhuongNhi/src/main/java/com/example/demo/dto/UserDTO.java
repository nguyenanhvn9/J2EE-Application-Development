package com.example.demo.dto;

import lombok.Generated;

public class UserDTO {
   private int id;
   private String name;
   private String username;
   private String email;
   private AddressDTO address;
   private String phone;
   private String website;
   private CompanyDTO company;

   @Generated
   public int getId() {
      return this.id;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getUsername() {
      return this.username;
   }

   @Generated
   public String getEmail() {
      return this.email;
   }

   @Generated
   public AddressDTO getAddress() {
      return this.address;
   }

   @Generated
   public String getPhone() {
      return this.phone;
   }

   @Generated
   public String getWebsite() {
      return this.website;
   }

   @Generated
   public CompanyDTO getCompany() {
      return this.company;
   }

   @Generated
   public void setId(final int id) {
      this.id = id;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setUsername(final String username) {
      this.username = username;
   }

   @Generated
   public void setEmail(final String email) {
      this.email = email;
   }

   @Generated
   public void setAddress(final AddressDTO address) {
      this.address = address;
   }

   @Generated
   public void setPhone(final String phone) {
      this.phone = phone;
   }

   @Generated
   public void setWebsite(final String website) {
      this.website = website;
   }

   @Generated
   public void setCompany(final CompanyDTO company) {
      this.company = company;
   }

   @Generated
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof UserDTO)) {
         return false;
      } else {
         UserDTO other = (UserDTO)o;
         if (!other.canEqual(this)) {
            return false;
         } else if (this.getId() != other.getId()) {
            return false;
         } else {
            label97: {
               Object this$name = this.getName();
               Object other$name = other.getName();
               if (this$name == null) {
                  if (other$name == null) {
                     break label97;
                  }
               } else if (this$name.equals(other$name)) {
                  break label97;
               }

               return false;
            }

            Object this$username = this.getUsername();
            Object other$username = other.getUsername();
            if (this$username == null) {
               if (other$username != null) {
                  return false;
               }
            } else if (!this$username.equals(other$username)) {
               return false;
            }

            Object this$email = this.getEmail();
            Object other$email = other.getEmail();
            if (this$email == null) {
               if (other$email != null) {
                  return false;
               }
            } else if (!this$email.equals(other$email)) {
               return false;
            }

            label76: {
               Object this$address = this.getAddress();
               Object other$address = other.getAddress();
               if (this$address == null) {
                  if (other$address == null) {
                     break label76;
                  }
               } else if (this$address.equals(other$address)) {
                  break label76;
               }

               return false;
            }

            Object this$phone = this.getPhone();
            Object other$phone = other.getPhone();
            if (this$phone == null) {
               if (other$phone != null) {
                  return false;
               }
            } else if (!this$phone.equals(other$phone)) {
               return false;
            }

            Object this$website = this.getWebsite();
            Object other$website = other.getWebsite();
            if (this$website == null) {
               if (other$website != null) {
                  return false;
               }
            } else if (!this$website.equals(other$website)) {
               return false;
            }

            Object this$company = this.getCompany();
            Object other$company = other.getCompany();
            if (this$company == null) {
               if (other$company != null) {
                  return false;
               }
            } else if (!this$company.equals(other$company)) {
               return false;
            }

            return true;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof UserDTO;
   }

   @Generated
   public int hashCode() {
      boolean PRIME = true;
      int result = 1;
      result = result * 59 + this.getId();
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $username = this.getUsername();
      result = result * 59 + ($username == null ? 43 : $username.hashCode());
      Object $email = this.getEmail();
      result = result * 59 + ($email == null ? 43 : $email.hashCode());
      Object $address = this.getAddress();
      result = result * 59 + ($address == null ? 43 : $address.hashCode());
      Object $phone = this.getPhone();
      result = result * 59 + ($phone == null ? 43 : $phone.hashCode());
      Object $website = this.getWebsite();
      result = result * 59 + ($website == null ? 43 : $website.hashCode());
      Object $company = this.getCompany();
      result = result * 59 + ($company == null ? 43 : $company.hashCode());
      return result;
   }

   @Generated
   public String toString() {
      int var10000 = this.getId();
      return "UserDTO(id=" + var10000 + ", name=" + this.getName() + ", username=" + this.getUsername() + ", email=" + this.getEmail() + ", address=" + String.valueOf(this.getAddress()) + ", phone=" + this.getPhone() + ", website=" + this.getWebsite() + ", company=" + String.valueOf(this.getCompany()) + ")";
   }

   @Generated
   public UserDTO() {
   }
}
