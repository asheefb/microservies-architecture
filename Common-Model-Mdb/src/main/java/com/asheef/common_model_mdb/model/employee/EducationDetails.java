package com.asheef.common_model_mdb.model.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EducationDetails {

        private String educationLevel;     // e.g., "SSLC", "PUC", "Undergraduate"

        private String boardName;          // e.g., "Karnataka State Board", "CBSE"

        private String schoolOrCollegeName;// e.g., "St. Joseph's School"

        private String location;           // e.g., "Mangalore"

        private String passOut;        // e.g., "2018"

        private Double percentage;         // e.g., 92.4

        private String grade;              // e.g., "A+", "First Class"

        private String medium;             // e.g., "English", "Kannada"

        private String stream;             // For PUC: "Science", "Commerce", etc. (can be null for SSLC)

}
