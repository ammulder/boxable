/*
 Quodlibet.be
 */
package be.quodlibet.boxable;

import be.quodlibet.boxable.utils.ImageUtils;
import com.google.common.io.Files;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.junit.Test;

public class TableTest {

	@Test
	public void Sample1() throws IOException {

		//Set margins
		float margin = 10;

		List<String[]> facts = getFacts();

		//Initialize Document
		PDDocument doc = new PDDocument();
		PDPage page = addNewPage(doc);
		float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);

		//Initialize table
		float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
		boolean drawContent = true;
		float yStart = yStartNewPage;
		float bottomMargin = 70;
		BaseTable table = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth, margin, doc, page, true,
				drawContent);

		//Create Header row
		Row<PDPage> headerRow = table.createRow(15f);
		Cell<PDPage> cell = headerRow.createCell(100, "Awesome Facts About Belgium");
		cell.setFont(PDType1Font.HELVETICA_BOLD);
		cell.setFillColor(Color.BLACK);
		cell.setTextColor(Color.WHITE);

		table.addHeaderRow(headerRow);

		//Create 2 column row
		Row<PDPage> row = table.createRow(15f);
		cell = row.createCell(30, "Source:");
		cell.setFont(PDType1Font.HELVETICA);

		cell = row.createCell(70, "http://www.factsofbelgium.com/");
		cell.setFont(PDType1Font.HELVETICA_OBLIQUE);

		//Create Fact header row
		Row<PDPage> factHeaderrow = table.createRow(15f);

		cell = factHeaderrow.createCell((100 / 3f) * 2, "Fact");
		cell.setFont(PDType1Font.HELVETICA);
		cell.setFontSize(6);
		cell.setFillColor(Color.LIGHT_GRAY);

		cell = factHeaderrow.createCell((100 / 3f), "Tags");
		cell.setFillColor(Color.LIGHT_GRAY);
		cell.setFont(PDType1Font.HELVETICA_OBLIQUE);
		cell.setFontSize(6);

		//Add multiple rows with random facts about Belgium
		for (String[] fact : facts) {

			row = table.createRow(10f);
			cell = row.createCell((100 / 3f) * 2, fact[0]);
			cell.setFont(PDType1Font.HELVETICA);
			cell.setFontSize(6);

			for (int i = 1; i < fact.length; i++) {
				if (fact[i].startsWith("image:")) {
					File imageFile;
					try {
						imageFile = new File(
								TableTest.class.getResource("/" + fact[i].substring("image:".length())).toURI());
						cell = row.createImageCell((100 / 9f), ImageUtils.readImage(imageFile));
					} catch (final URISyntaxException e) {
						e.printStackTrace();
					}
				} else {
					cell = row.createCell((100 / 9f), fact[i]);
					cell.setFont(PDType1Font.HELVETICA_OBLIQUE);
					cell.setFontSize(6);
					//Set colors
					if (fact[i].contains("beer"))
						cell.setFillColor(Color.yellow);
					if (fact[i].contains("champion"))
						cell.setTextColor(Color.GREEN);
				}
			}
		}

		table.draw();

		//Close Stream and save pdf
		File file = new File("target/BoxableSample1.pdf");
		System.out.println("Sample file saved at : " + file.getAbsolutePath());
		Files.createParentDirs(file);
		doc.save(file);
		doc.close();

	}

	private static List<String[]> getFacts() {
		List<String[]> facts = new ArrayList<String[]>();
		facts.add(new String[] { "Oil Painting was invented by the Belgian van Eyck brothers", "art", "inventions",
				"science" });
		facts.add(new String[] { "The Belgian Adolphe Sax invented the Saxophone", "inventions", "music", "" });
		facts.add(new String[] { "11 sites in Belgium are on the UNESCO World Heritage List", "art", "history", "" });
		facts.add(new String[] { "Belgium was the second country in the world to legalize same-sex marriage",
				"politics", "image:150dpi.png", "" });
		facts.add(new String[] { "In the seventies, schools served light beer during lunch", "health", "school",
				"beer" });
		facts.add(new String[] { "Belgium has the sixth fastest domestic internet connection in the world", "science",
				"technology", "" });
		facts.add(new String[] { "Belgium hosts the World's Largest Sand Sculpture Festival", "art", "festivals",
				"world championship" });
		facts.add(
				new String[] { "Belgium has compulsary education between the ages of 6 and 18", "education", "", "" });
		facts.add(new String[] {
				"Belgium also has more comic makers per square kilometer than any other country in the world", "art",
				"social", "world championship" });
		facts.add(new String[] {
				"Belgium has one of the lowest proportion of McDonald's restaurants per inhabitant in the developed world",
				"food", "health", "" });
		facts.add(new String[] { "Belgium has approximately 178 beer breweries", "beer", "food", "" });
		facts.add(new String[] { "Gotye was born in Bruges, Belgium", "music", "celebrities", "" });
		facts.add(new String[] { "The Belgian Coast Tram is the longest tram line in the world", "technology",
				"world championship", "" });
		facts.add(new String[] { "Stefan Everts is the only motocross racer with 10 World Championship titles.",
				"celebrities", "sports", "world champions" });
		facts.add(new String[] { "Tintin was conceived by Belgian artist Hergé", "art", "celebrities", "inventions" });
		facts.add(new String[] { "Brussels Airport is the world's biggest selling point of chocolate", "food",
				"world champions", "" });
		facts.add(new String[] { "Tomorrowland is the biggest electronic dance music festival in the world",
				"festivals", "music", "world champion" });
		facts.add(new String[] { "French Fries are actually from Belgium", "food", "inventions", "image:300dpi.png" });
		facts.add(new String[] { "Herman Van Rompy is the first full-time president of the European Council",
				"politics", "", "" });
		facts.add(new String[] { "Belgians are the fourth most money saving people in the world", "economy", "social",
				"" });
		facts.add(new String[] {
				"The Belgian highway system is the only man-made structure visible from the moon at night",
				"technology", "world champions", "" });
		facts.add(new String[] { "Andreas Vesalius, the founder of modern human anatomy, is from Belgium",
				"celebrities", "education", "history" });
		facts.add(
				new String[] { "Napoleon was defeated in Waterloo, Belgium", "celebrities", "history", "politicians" });
		facts.add(new String[] {
				"The first natural color picture in National Geographic was of a flower garden in Gent, Belgium in 1914",
				"art", "history", "science" });
		facts.add(new String[] { "Rock Werchter is the Best Festival in the World", "festivals", "music",
				"world champions" });

		//Make the table a bit bigger
		facts.addAll(facts);
		facts.addAll(facts);
		facts.addAll(facts);

		return facts;
	}

	@Test
	public void SampleTest2() throws IOException {

		//Set margins
		float margin = 10;

		List<String[]> facts = getFacts();
		facts.addAll(getFacts());//ensure we have multiple pages

		//A list of bookmarks of all the tables
		List<PDOutlineItem> bookmarks = new ArrayList<PDOutlineItem>();

		//Initialize Document
		PDDocument doc = new PDDocument();
		PDPage page = addNewPage(doc);

		//Initialize table
		float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
		float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
		boolean drawContent = true;
		boolean drawLines = true;
		float yStart = yStartNewPage;
		float bottomMargin = 70;
		BaseTable table = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth, margin, doc, page, drawLines,
				drawContent);

		//Create Header row
		Row<PDPage> headerRow = table.createRow(15f);
		Cell<PDPage> cell = headerRow.createCell(100, "Awesome Facts About Belgium");
		cell.setFont(PDType1Font.HELVETICA_BOLD);
		cell.setFillColor(Color.BLACK);
		cell.setTextColor(Color.WHITE);

		table.addHeaderRow(headerRow);

		//Create 2 column row
		Row<PDPage> row = table.createRow(15f);
		cell = row.createCell(75, "Source:");
		cell.setFont(PDType1Font.HELVETICA);

		cell = row.createCell(25, "http://www.factsofbelgium.com/");
		cell.setFont(PDType1Font.HELVETICA_OBLIQUE);

		//Create Fact header row
		Row<PDPage> factHeaderrow = table.createRow(15f);
		cell = factHeaderrow.createCell((100 / 3f) * 2, "Fact");
		cell.setFont(PDType1Font.HELVETICA);
		cell.setFontSize(6);
		cell.setFillColor(Color.LIGHT_GRAY);

		cell = factHeaderrow.createCell((100 / 3f), "Tags");
		cell.setFillColor(Color.LIGHT_GRAY);
		cell.setFont(PDType1Font.HELVETICA_OBLIQUE);
		cell.setFontSize(6);

		//Add multiple rows with random facts about Belgium
		int bookmarkid = 0;
		for (String[] fact : facts) {

			row = table.createRow(10f);
			cell = row.createCell((100 / 3.0f) * 2, fact[0] + " " + fact[0] + " " + fact[0]);
			cell.setFont(PDType1Font.HELVETICA);
			cell.setFontSize(6);

			//Create a bookmark for each record
			PDOutlineItem outlineItem = new PDOutlineItem();
			outlineItem.setTitle((++bookmarkid) + ") " + fact[0]);
			row.setBookmark(outlineItem);

			for (int i = 1; i < fact.length; i++) {
				if (fact[i].startsWith("image:")) {
					File imageFile;
					try {
						imageFile = new File(
								TableTest.class.getResource("/" + fact[i].substring("image:".length())).toURI());
						cell = row.createImageCell((100 / 9f), ImageUtils.readImage(imageFile));
					} catch (final URISyntaxException e) {
						e.printStackTrace();
					}
				} else {
					cell = row.createCell((100 / 9f), fact[i]);
					cell.setFont(PDType1Font.HELVETICA_OBLIQUE);
					cell.setFontSize(6);

					//Set colors
					if (fact[i].contains("beer"))
						cell.setFillColor(Color.yellow);
					if (fact[i].contains("champion"))
						cell.setTextColor(Color.GREEN);
				}

			}
		}
		table.draw();

		//Get all bookmarks of previous table
		bookmarks.addAll(table.getBookmarks());

		//Create document outline
		PDDocumentOutline outline = new PDDocumentOutline();

		for (PDOutlineItem bm : bookmarks) {
			outline.addLast(bm);
		}

		doc.getDocumentCatalog().setDocumentOutline(outline);

		//Save the document
		File file = new File("target/BoxableSample2.pdf");
		System.out.println("Sample file saved at : " + file.getAbsolutePath());
		Files.createParentDirs(file);
		doc.save(file);
		doc.close();

	}

	/**
	 * <p>
	 * Sample test for text rotation
	 * </p>
	 *
	 * @throws IOException
	 */
	@Test
	public void SampleTest3() throws IOException {
		//Set margins
		float margin = 10;

		//Initialize Document
		PDDocument doc = new PDDocument();
		PDPage page = addNewPage(doc);

		//Initialize table
		float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
		float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
		boolean drawContent = true;
		boolean drawLines = true;
		float yStart = yStartNewPage;
		float bottomMargin = 70;
		BaseTable table = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth, margin, doc, page, drawLines,
				drawContent);

		//Create Header row
		Row<PDPage> row = table.createRow(15f);
		Cell<PDPage> cell = row.createCell((100 / 3f), "Hello", HorizontalAlignment.get("center"),
				VerticalAlignment.get("top"));
		cell.setTextRotated(true);
		cell.setFont(PDType1Font.HELVETICA);
		cell.setFontSize(6);

		Cell<PDPage> cell2 = row.createCell((100 / 3f), "It's me", HorizontalAlignment.get("center"),
				VerticalAlignment.get("middle"));
		cell2.setTextRotated(true);
		cell2.setFont(PDType1Font.HELVETICA);
		cell2.setFontSize(6);

		Cell<PDPage> cell3 = row.createCell((100 / 3f), "I was wondering", HorizontalAlignment.get("center"),
				VerticalAlignment.get("bottom"));
		cell3.setTextRotated(true);
		cell3.setFont(PDType1Font.HELVETICA);
		cell3.setFontSize(6);

		Row<PDPage> row2 = table.createRow(15f);
		Cell<PDPage> cell4 = row2.createCell((100 / 3.0f), "Hello", HorizontalAlignment.get("center"),
				VerticalAlignment.get("top"));
		cell4.setFont(PDType1Font.HELVETICA);
		cell4.setFontSize(6);

		Cell<PDPage> cell5 = row2.createCell((100 / 3f), "can you hear me?", HorizontalAlignment.get("center"),
				VerticalAlignment.get("middle"));
		cell5.setTextRotated(true);
		cell5.setFont(PDType1Font.HELVETICA);
		cell5.setFontSize(6);

		Cell<PDPage> cell6 = row2.createCell((100 / 3f),
				"I'm in California dreaming about who we used to be. When we were younger and free. I've forgotten how it felt before the world fell at our feet",
				HorizontalAlignment.get("center"), VerticalAlignment.get("bottom"));
		cell6.setFont(PDType1Font.HELVETICA);
		cell6.setFontSize(6);
		table.draw();

		//Save the document
		File file = new File("target/BoxableSample3.pdf");
		System.out.println("Sample file saved at : " + file.getAbsolutePath());
		Files.createParentDirs(file);
		doc.save(file);
		doc.close();
	}

	/**
	 * <p>
	 * Multiple header rows in the table
	 * </p>
	 *
	 * @throws IOException
	 */
	@Test
	public void SampleTest4() throws IOException {

		//Set margins
		float margin = 10;

		List<String[]> facts = getFacts();
		facts.addAll(getFacts());//ensure we have multiple pages

		//A list of bookmarks of all the tables
		List<PDOutlineItem> bookmarks = new ArrayList<PDOutlineItem>();

		//Initialize Document
		PDDocument doc = new PDDocument();
		PDPage page = addNewPage(doc);

		//Initialize table
		float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
		float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
		boolean drawContent = true;
		boolean drawLines = true;
		float yStart = yStartNewPage;
		float bottomMargin = 70;
		BaseTable table = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth, margin, doc, page, drawLines,
				drawContent);

		//Create header row
		Row<PDPage> headerRow = table.createRow(15f);
		Cell<PDPage> cell = headerRow.createCell(100, "Awesome Facts About Belgium");
		cell.setFont(PDType1Font.HELVETICA_BOLD);
		cell.setFillColor(Color.BLACK);
		cell.setTextColor(Color.WHITE);

		table.addHeaderRow(headerRow);

		//Create second header row
		Row<PDPage> secondHeaderRow = table.createRow(15f);
		cell = secondHeaderRow.createCell(75, "Source:");
		cell.setFont(PDType1Font.HELVETICA);

		cell = secondHeaderRow.createCell(25, "http://www.factsofbelgium.com/");
		cell.setFont(PDType1Font.HELVETICA_OBLIQUE);

		table.addHeaderRow(secondHeaderRow);

		// create fact header row
		Row<PDPage> factHeaderrow = table.createRow(15f);
		cell = factHeaderrow.createCell((100 / 3f) * 2, "Fact");
		cell.setFont(PDType1Font.HELVETICA);
		cell.setFontSize(6);
		cell.setFillColor(Color.LIGHT_GRAY);

		cell = factHeaderrow.createCell((100 / 3f), "Tags");
		cell.setFillColor(Color.LIGHT_GRAY);
		cell.setFont(PDType1Font.HELVETICA_OBLIQUE);
		cell.setFontSize(6);

		table.addHeaderRow(factHeaderrow);

		//Add multiple rows with random facts about Belgium
		int bookmarkid = 0;
		for (String[] fact : facts) {

			Row<PDPage> row = table.createRow(10f);
			cell = row.createCell((100 / 3.0f) * 2, fact[0] + " " + fact[0] + " " + fact[0]);
			cell.setFont(PDType1Font.HELVETICA);
			cell.setFontSize(6);

			//Create a bookmark for each record
			PDOutlineItem outlineItem = new PDOutlineItem();
			outlineItem.setTitle((++bookmarkid) + ") " + fact[0]);
			row.setBookmark(outlineItem);

			for (int i = 1; i < fact.length; i++) {
				if (fact[i].startsWith("image:")) {
					File imageFile;
					try {
						imageFile = new File(
								TableTest.class.getResource("/" + fact[i].substring("image:".length())).toURI());
						cell = row.createImageCell((100 / 9f), ImageUtils.readImage(imageFile));
					} catch (final URISyntaxException e) {
						e.printStackTrace();
					}
				} else {
					cell = row.createCell((100 / 9f), fact[i]);
					cell.setFont(PDType1Font.HELVETICA_OBLIQUE);
					cell.setFontSize(6);

					//Set colors
					if (fact[i].contains("beer"))
						cell.setFillColor(Color.yellow);
					if (fact[i].contains("champion"))
						cell.setTextColor(Color.GREEN);
				}

			}
		}
		table.draw();

		//Get all bookmarks of previous table
		bookmarks.addAll(table.getBookmarks());

		//Create document outline
		PDDocumentOutline outline = new PDDocumentOutline();

		for (PDOutlineItem bm : bookmarks) {
			outline.addLast(bm);
		}

		doc.getDocumentCatalog().setDocumentOutline(outline);

		//Save the document
		File file = new File("target/BoxableSample4.pdf");
		System.out.println("Sample file saved at : " + file.getAbsolutePath());
		Files.createParentDirs(file);
		doc.save(file);
		doc.close();

	}

	@Test
	public void SampleTest5() throws IOException {

		//Set margins
		float margin = 10;

		//Initialize Document
		PDDocument doc = new PDDocument();
		PDPage page = addNewPage(doc);

		//Initialize table
		float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
		float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
		boolean drawContent = true;
		boolean drawLines = true;
		float yStart = yStartNewPage;
		float bottomMargin = 70;
		BaseTable table = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth, margin, doc, page, drawLines,
				drawContent);

		//Create Header row
		Row<PDPage> row = table.createRow(15f);
		Cell<PDPage> cell = row.createCell((100 / 3f), "<b>Here is bold</b>", HorizontalAlignment.get("center"),
				VerticalAlignment.get("top"));
		cell.setFontSize(6);

		Cell<PDPage> cell2 = row.createCell((100 / 3f), "<i>Here is text in italic</i>", HorizontalAlignment.get("center"),
				VerticalAlignment.get("middle"));
		cell2.setFontSize(6);

		Cell<PDPage> cell3 = row.createCell((100 / 3f), "<b><i>Here is text in bold and italic</i></b>", HorizontalAlignment.get("center"),
				VerticalAlignment.get("bottom"));
		cell3.setFontSize(6);

		Row<PDPage> row2 = table.createRow(15f);
		Cell<PDPage> cell4 = row2.createCell((100 / 3.0f), " <p>Integer eget elit vitae est feugiat laoreet. <b>Nam vitae ex commodo, euismod risus in, sodales dolor. Mauris condimentum urna neque, non condimentum odio</b> posuere a. Aenean nisl ex, semper eu malesuada sit amet, luctus nec enim. <br>Pellentesque eu ultrices magna, non porta dolor. Fus<b><i>ce eu neque nulla. Curabitur eu eros tristique leo efficitur fringilla sit amet sed neque. Aliquam</i></b> a tempor enim. Praesent pellentesque volutpat dolor, non rhoncus est posuere id. Aenean nunc purus, gravida at mauris et, pretium volutpat nisl. Mauris lacus urna, sodales ac eros in, mollis scelerisque neque.</p> Unordered List <ul><li>Item 1</li><li>Item 2</li><li>Item 3</li></ul>", HorizontalAlignment.get("center"),
				VerticalAlignment.get("top"));
		cell4.setFontSize(6);

		Cell<PDPage> cell5 = row2.createCell((100 / 3f), "<p>Proin dui dolor, lacinia at dui at, placerat ullamcorper arcu. Sed auctor sagittis elit, at eleifend ex aliquet ut. Duis lobortis est nec placerat condimentum. Aliquam erat volutpat. In a sem massa. Phasellus eget tortor iaculis, condimentum turpis a, sodales lorem. Aenean egestas congue ex<i> eu condimentum. Fusce sed</i> fringilla lorem. Vestibulum luctus ni<b>si ac turpis congue, vitae pharetra lorem suscipit.</b></p>Ordered List <ol><li>Item 1</li><li>Item 2</li><li>Item 3</li></ol>", HorizontalAlignment.get("center"),
				VerticalAlignment.get("middle"));
		cell5.setFontSize(6);

		Cell<PDPage> cell6 = row2.createCell((100 / 3f),
				"<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam at bibendum leo. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aenean lobortis enim vitae magna varius, nec scelerisque sapien elementum. Quisque porta eros in feugiat commodo. Phasellus a elit diam. Nullam pretium lorem malesuada, ullamcorper risus eget, dictum libero. Nulla neque ante, volutpat in tincidunt eu, porttitor ut purus. Fusce at mauris velit. Pellentesque vel tincidunt erat. </p><p>In vehicula velit nunc, sit amet ultricies neque fringilla vel. Quisque ac enim nisl. Ut quis leo et lorem iaculis porttitor a semper diam. Pellentesque lobortis nisi ac ipsum efficitur facilisis. Interdum et malesuada fames ac ante ipsum primis in faucibus. Praesent in tellus velit. Maecenas volutpat ipsum lacus, non fringilla neque faucibus et.</p>",
				HorizontalAlignment.get("center"), VerticalAlignment.get("bottom"));
		cell6.setFontSize(6);
		table.draw();

		//Save the document
		File file = new File("target/BoxableSample5.pdf");
		System.out.println("Sample file saved at : " + file.getAbsolutePath());
		Files.createParentDirs(file);
		doc.save(file);
		doc.close();
	}

	@Test
	public void SampleTest6() throws IOException {

		//Set margins
		float margin = 10;

		//Initialize Document
		PDDocument doc = new PDDocument();
		PDPage page = addNewPage(doc);

		//Initialize table
		float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
		float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
		boolean drawContent = true;
		boolean drawLines = true;
		float yStart = yStartNewPage;
		float bottomMargin = 70;
		BaseTable table = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth, margin, doc, page, drawLines,
				drawContent);

		//Create Header row
		Row<PDPage> row = table.createRow(15f);
		Cell<PDPage> cell = row.createCell((100 / 3f), "Hellooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo", HorizontalAlignment.get("center"),
				VerticalAlignment.get("top"));
		cell.setFontSize(6);

		Cell<PDPage> cell2 = row.createCell((100 / 3f), "<i>Here is text in italic</i>", HorizontalAlignment.get("center"),
				VerticalAlignment.get("middle"));
		cell2.setFontSize(6);

		Cell<PDPage> cell3 = row.createCell((100 / 3f), "<b><i>Here is text in bold and italic</i></b>", HorizontalAlignment.get("center"),
				VerticalAlignment.get("bottom"));
		cell3.setFontSize(6);
		table.draw();

		//Save the document
		File file = new File("target/BoxableSample6.pdf");
		System.out.println("Sample file saved at : " + file.getAbsolutePath());
		Files.createParentDirs(file);
		doc.save(file);
		doc.close();
    }

    @Test
    public void SampleTest7() throws IOException
    {

        //Set margins
        float margin = 10;

        //Initialize Document
        PDDocument doc = new PDDocument();
        PDPage page = addNewPage(doc);

        //Initialize table
        float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
        float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
        boolean drawContent = true;
        boolean drawLines = true;
        float yStart = yStartNewPage;
        float bottomMargin = 70;
        BaseTable table = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth, margin, doc, page, drawLines,
                                        drawContent);

        //Create Header row
        Row<PDPage> row = table.createRow(400f);
        Cell<PDPage> cell = row.createCell((100 / 3f), "<p>Integer eget elit vitae est feugiat laoreet. <b>Nam vitae ex commodo, euismod risus in, sodales dolor. Mauris condimentum urna neque, non condimentum odio</b> posuere a. Aenean nisl ex, semper eu malesuada sit amet, luctus nec enim. <br>Pellentesque eu ultrices magna, non porta dolor. Fus<b><i>ce eu neque nulla. Curabitur eu eros tristique leo efficitur fringilla sit amet sed neque. Aliquam</i></b> a tempor enim. Praesent pellentesque volutpat dolor, non rhoncus est posuere id. Aenean nunc purus, gravida at mauris et, pretium volutpat nisl. Mauris lacus urna, sodales ac eros in, mollis scelerisque neque.</p> Unordered List <ul><li>Item 1</li><li>Item 2</li><li>Item 3</li></ul>", HorizontalAlignment.get("center"),
                                           VerticalAlignment.get("top"));
        cell.setFontSize(6);

        Cell<PDPage> cell2 = row.createCell((100 / 3f), "<p>Proin dui dolor, lacinia at dui at, placerat ullamcorper arcu. Sed auctor sagittis elit, at eleifend ex aliquet ut. Duis lobortis est nec placerat condimentum. Aliquam erat volutpat. In a sem massa. Phasellus eget tortor iaculis, condimentum turpis a, sodales lorem. Aenean egestas congue ex<i> eu condimentum. Fusce sed</i> fringilla lorem. Vestibulum luctus ni<b>si ac turpis congue, vitae pharetra lorem suscipit.</b></p>Ordered List <ol><li>Item 1</li><li>Item 2</li><li>Item 3</li></ol>", HorizontalAlignment.get("center"),
                                            VerticalAlignment.get("middle"));
        cell2.setFontSize(6);

        Cell<PDPage> cell3 = row.createCell((100 / 3f), "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam at bibendum leo. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aenean lobortis enim vitae magna varius, nec scelerisque sapien elementum. Quisque porta eros in feugiat commodo. Phasellus a elit diam. Nullam pretium lorem malesuada, ullamcorper risus eget, dictum libero. Nulla neque ante, volutpat in tincidunt eu, porttitor ut purus. Fusce at mauris velit. Pellentesque vel tincidunt erat. </p><p>In vehicula velit nunc, sit amet ultricies neque fringilla vel. Quisque ac enim nisl. Ut quis leo et lorem iaculis porttitor a semper diam. Pellentesque lobortis nisi ac ipsum efficitur facilisis. Interdum et malesuada fames ac ante ipsum primis in faucibus. Praesent in tellus velit. Maecenas volutpat ipsum lacus, non fringilla neque faucibus et.</p>", HorizontalAlignment.get("center"),
                                            VerticalAlignment.get("bottom"));
        cell3.setFontSize(6);

        Row<PDPage> row2 = table.createRow(400f);
        Cell<PDPage> cell4 = row2.createCell((100 / 3.0f), " <p>Integer eget elit vitae est feugiat laoreet. <b>Nam vitae ex commodo, euismod risus in, sodales dolor. Mauris condimentum urna neque, non condimentum odio</b> posuere a. Aenean nisl ex, semper eu malesuada sit amet, luctus nec enim. <br>Pellentesque eu ultrices magna, non porta dolor. Fus<b><i>ce eu neque nulla. Curabitur eu eros tristique leo efficitur fringilla sit amet sed neque. Aliquam</i></b> a tempor enim. Praesent pellentesque volutpat dolor, non rhoncus est posuere id. Aenean nunc purus, gravida at mauris et, pretium volutpat nisl. Mauris lacus urna, sodales ac eros in, mollis scelerisque neque.</p> Unordered List <ul><li>Item 1</li><li>Item 2</li><li>Item 3</li></ul>", HorizontalAlignment.get("center"),
                                             VerticalAlignment.get("top"));
        cell4.setFontSize(6);

        Cell<PDPage> cell5 = row2.createCell((100 / 3f), "<p>Proin dui dolor, lacinia at dui at, placerat ullamcorper arcu. Sed auctor sagittis elit, at eleifend ex aliquet ut. Duis lobortis est nec placerat condimentum. Aliquam erat volutpat. In a sem massa. Phasellus eget tortor iaculis, condimentum turpis a, sodales lorem. Aenean egestas congue ex<i> eu condimentum. Fusce sed</i> fringilla lorem. Vestibulum luctus ni<b>si ac turpis congue, vitae pharetra lorem suscipit.</b></p>Ordered List <ol><li>Item 1</li><li>Item 2</li><li>Item 3</li></ol>", HorizontalAlignment.get("center"),
                                             VerticalAlignment.get("middle"));
        cell5.setFontSize(6);

        Cell<PDPage> cell6 = row2.createCell((100 / 3f),
                                             "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam at bibendum leo. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aenean lobortis enim vitae magna varius, nec scelerisque sapien elementum. Quisque porta eros in feugiat commodo. Phasellus a elit diam. Nullam pretium lorem malesuada, ullamcorper risus eget, dictum libero. Nulla neque ante, volutpat in tincidunt eu, porttitor ut purus. Fusce at mauris velit. Pellentesque vel tincidunt erat. </p><p>In vehicula velit nunc, sit amet ultricies neque fringilla vel. Quisque ac enim nisl. Ut quis leo et lorem iaculis porttitor a semper diam. Pellentesque lobortis nisi ac ipsum efficitur facilisis. Interdum et malesuada fames ac ante ipsum primis in faucibus. Praesent in tellus velit. Maecenas volutpat ipsum lacus, non fringilla neque faucibus et.</p>",
                                             HorizontalAlignment.get("center"), VerticalAlignment.get("bottom"));
        cell6.setFontSize(6);
        table.draw();

        //Save the document
        File file = new File("target/BoxableSample7.pdf");
        System.out.println("Sample file saved at : " + file.getAbsolutePath());
        Files.createParentDirs(file);
        doc.save(file);
        doc.close();
    }

	private static PDPage addNewPage(PDDocument doc) {
		PDPage page = new PDPage();
		doc.addPage(page);
		return page;
	}
}
