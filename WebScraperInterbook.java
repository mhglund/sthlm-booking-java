package com.example.project;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;
import java.util.Arrays;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;

public class WebScraperInterbook {

    public static void main(String[] args) {

        // Välj vilken anläggning som en vill söka på
        String searchedField = "Bagarmossens BP";
        
        //String searchedActivity = "Fotboll";


        String baseUrl = "https://booking.stockholm.se/SearchScheme/Search_Activity" +
            ".aspx?FromMenu=True";
        
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);

        try {

            String searchUrl = baseUrl;

            // Hämtar sidan
            HtmlPage page = client.getPage(searchUrl);

            // Väljer "dropdownen" med planer och väljer plan
            DomElement myFieldInput = page.getElementById("drplFacility");
            myFieldInput.setAttribute("value", searchedField);

            // Väljer att visa alla typer utav aktiviteter (automatiskt)
            //DomElement myActivityInput = page.getElementById("drplFacility");
            //myActivityInput.setAttribute("value", searchedActivity);

            // Dagens datum väljs automatiskt, därför är denna kod överflödig
            /*HtmlElement dateFrom = ((HtmlElement)
              page.getFirstByXPath(".//table[@id='calDate']//a[text()='16']")); 
              page = (HtmlPage) dateFrom.click(); */

            // Klickar fram tre månader
            for (int i=0; i<3; i++) {
                HtmlElement changeMonth = (HtmlElement)
                    page.getFirstByXPath(".//table[@id='calDateTo']//a[@title='Go to the next month']");
                page = (HtmlPage) changeMonth.click();
            } 

            // Väljer den 28:e (tre månader fram) och klickar på den
            HtmlElement dateTo = ((HtmlElement)
                                  page.getFirstByXPath(".//table[@id='calDateTo']//a[text()='28']")); 
            page = (HtmlPage) dateTo.click();

            // Väljer knappen "Visa aktivitetslista" och klickar på den
            HtmlSubmitInput showButton = (HtmlSubmitInput) page.getElementById("btnActivity");
            HtmlPage resultPage = (HtmlPage) showButton.click();

            //System.out.println(resultPage.asText());

            
            HtmlTable resultTable = resultPage.getHtmlElementById("dgResult");
            int rowNr = 0;
            for (HtmlTableRow row : resultTable.getRows()) {
                if (rowNr == 0) {
                    // Första raden är rubriker, och hoppas därför över
                    rowNr++;
                    continue;
                } else {
                    String aktivitet = "", forening = "", hemsida = "", plats = "";
                    LocalDate theDate = LocalDate.of(1900, Month.JANUARY, 1);
                    LocalTime startTid = LocalTime.of(00,00,00,00), slutTid = LocalTime.of(01,00,00,00);

                    int cellNr = 0;
                    for (HtmlTableCell cell : row.getCells()) {
                        switch (cellNr) {
                        case 0:
                            aktivitet = cell.asText(); 

                            break;
                        case 1:
                            String datum = cell.asText();
                            theDate = LocalDate.parse(datum);
                                                        
                            break;
                        case 2:
                            String tid = cell.asText();

                            LocalTime[] times =
                                Arrays.stream(tid.split("-"))
                                .map(military -> LocalTime.parse(military, DateTimeFormatter.ofPattern("HHmm")))
                                .toArray(LocalTime[]::new);
                            startTid = times[0];
                            slutTid = times[1]; 
                            
                            break;
                        case 3:
                            forening = cell.asText();

                            break;
                        case 4:
                            hemsida = cell.asText();

                            break;
                        case 5:
                            plats = cell.asText();

                            break;
                        default:
                            System.out.println("Fel på cell/tabell?");
                            break;
                        } 

                        cellNr++;
                    }
                    // SKAPA NY BOKAD AKTIVITET och skriv ut
                    BookedActivity bokad = new BookedActivity(theDate, startTid, slutTid, forening, plats, hemsida, aktivitet);
                    System.out.println(bokad);
                }
                rowNr++;
            }

        } catch(Exception e) {
            e.printStackTrace();
            
        }

        // Städa upp efter oss.
        // Undviker förvirrande felmeddelande vid körning
        client.closeAllWindows();
    }
    
}
