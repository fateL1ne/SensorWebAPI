package sk.tuke.SensorWebApi.server.http.response;

import sk.tuke.SensorWebApi.server.jpa.entities.core.Office;
import sk.tuke.SensorWebApi.server.jpa.entities.reports.office.MonthlyOfficeSuggestion;

import java.util.ArrayList;
import java.util.List;


public class OfficeSuggestionsResponse
{

    private List<OfficeSuggestionResponse> officeSuggestionResponses = new ArrayList<>();

      public OfficeSuggestionsResponse(List<MonthlyOfficeSuggestion> monthlyOfficeSuggestions) {

          monthlyOfficeSuggestions.forEach( monthlyOfficeSuggestion ->
          {
              Office office = monthlyOfficeSuggestion.getOffice();

              officeSuggestionResponses.add( new OfficeSuggestionResponse(
                      monthlyOfficeSuggestion.getId(),
                      office.getId(), office.getOfficeName(), office.getDesks().size() ));
          });
      }

    public List<OfficeSuggestionResponse> getOfficeSuggestionResponses() {
        return officeSuggestionResponses;
    }

    public void setOfficeSuggestionResponses(List<OfficeSuggestionResponse> officeSuggestionResponses) {
        this.officeSuggestionResponses = officeSuggestionResponses;
    }

    public static class OfficeSuggestionResponse {
            private Long Id;
            private Long officeId;
            private String officeName;
            private int desksCount;

            public OfficeSuggestionResponse(Long id, Long officeId, String officeName, int desksCount) {
                Id = id;
                this.officeId = officeId;
                this.officeName = officeName;
                this.desksCount = desksCount;
            }

            public OfficeSuggestionResponse(Office office) {
                this.officeId = office.getId();
                this.officeName = getOfficeName();
                this.desksCount = office.getDesks().size();
            }


            public Long getOfficeId() {
                return officeId;
            }

            public void setOfficeId(Long officeId) {
                this.officeId = officeId;
            }

            public String getOfficeName() {
                return officeName;
            }

            public void setOfficeName(String officeName) {
                this.officeName = officeName;
            }

            public int getDesksCount() {
                return desksCount;
            }

            public void setDesksCount(int desksCount) {
                this.desksCount = desksCount;
            }

            public Long getId() {
                return Id;
            }

            public void setId(Long id) {
                Id = id;
            }
        }
}
