package org.jboss.gwt.elemento.core;

import java.util.NoSuchElementException;

import com.google.common.collect.Iterables;
import elemental2.dom.Document;
import elemental2.dom.HTMLElement;
import org.junit.Before;
import org.junit.Test;

import static org.jboss.gwt.elemento.core.EventType.click;
import static org.jboss.gwt.elemento.core.InputType.number;
import static org.jboss.gwt.elemento.core.InputType.text;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Harald Pehl
 */
public class ElementsBuilderTest {

    private TestableBuilder builder;

    @Before
    public void setUp() {
        Document document = mock(Document.class);

        when(document.createElement(anyString())).thenAnswer(
                invocation -> new StandaloneElement(String.valueOf(invocation.getArguments()[0])));
        when(document.createElement("input")).thenAnswer(invocation -> new StandaloneInputElement("input"));

        builder = new TestableBuilder(document);
    }


    // ------------------------------------------------------ container elements

    @Test
    public void headings() {
        for (int i = 0; i < 6; i++) {
            assertEquals("<h" + i + ">Heading " + i + "</h" + i + ">",
                    builder.h(i).textContent("Heading " + i).end().build().toString());
        }
    }

    @Test
    public void headingsWithText() {
        for (int i = 0; i < 6; i++) {
            assertEquals("<h" + i + ">Heading " + i + "</h" + i + ">",
                    builder.h(i, "Heading " + i).end().build().toString());
        }
    }

    @Test
    public void p() {
        assertEquals("<p>Lorem ipsum</p>", builder.p().textContent("Lorem ipsum").end().build().toString());
    }

    @Test
    public void ol() {
        assertEquals("<ol><li>first</li><li>second</li></ol>",
                builder.ol().li().textContent("first").end().li().textContent("second").end().end().build().toString());
    }

    @Test
    public void ul() {
        assertEquals("<ul><li>foo</li><li>bar</li></ul>",
                builder.ul().li().textContent("foo").end().li().textContent("bar").end().end().build().toString());
    }

    @Test
    public void a() {
        assertEquals("<a title=\"Visit my blog\" href=\"http://hpehl.info/\">hpehl.info</a>",
                builder.a().attr("href", "http://hpehl.info/").title("Visit my blog").textContent("hpehl.info")
                        .end().build().toString());
    }

    @Test
    public void aHref() {
        assertEquals("<a title=\"Visit my blog\" href=\"http://hpehl.info/\">hpehl.info</a>",
                builder.a("http://hpehl.info/").title("Visit my blog").textContent("hpehl.info")
                        .end().build().toString());
    }

    @Test
    public void div() {
        assertEquals("<div/>", builder.div().end().build().toString());
    }

    @Test
    public void span() {
        assertEquals("<span/>", builder.span().end().build().toString());
    }


    // ------------------------------------------------------ forms

    @Test
    public void form() {
        // @formatter:off
        builder
            .form().attr("method", "get").attr("action", "search").css("form form-horizontal")
                .div().css("form-group")
                    .label().css("col-md-3 control-label").attr("for", "name").textContent("Name").end()
                    .div().css("col-md-9")
                        .input(text).id("name").css("form-control").attr("placeholder", "Enter your name")
                    .end()
                .end()
                .div().css("form-group")
                    .label().css("col-md-3 control-label").attr("for", "age").textContent("Age").end()
                    .div().css("col-md-9")
                        .input(number).id("age").css("form-control").attr("placeholder", "How old are you?")
                    .end()
                .end()
                .div().css("form-group")
                    .label().css("col-md-3 control-label").attr("for", "hobbies").textContent("Hobbies").end()
                    .div().css("col-md-9")
                        .textarea().attr("rows", "3").id("hobbies").css("form-control").end()
                        .span().css("help-block textarea").textContent("One item per line").end()
                    .end()
                .end()
                .div().css("form-group")
                    .label().css("col-md-3 control-label").attr("for", "choose").textContent("Choose").end()
                    .div().css("col-md-9")
                        .select().id("choose").css("form-control selectpicker")
                            .option().textContent("Lorem").end()
                            .option().textContent("ipsum").end()
                        .end()
                    .end()
                .end()
                .div().css("form-group")
                    .div().css("col-md-offset-3 col-md-9")
                        .div().css("pull-right form-buttons")
                            .button().css("btn btn-default btn-form").textContent("Cancel").end()
                            .button().css("btn btn-primary btn-form").textContent("Save").end()
                        .end()
                    .end()
                .end()
            .end();
        // @formatter:on

        String markup = "<form class=\"form form-horizontal\" method=\"get\" action=\"search\">" +
                "<div class=\"form-group\">" +
                "<label class=\"col-md-3 control-label\" for=\"name\">Name</label>" +
                "<div class=\"col-md-9\">" +
                "<input type=\"text\" id=\"name\" class=\"form-control\" placeholder=\"Enter your name\"/>" +
                "</div>" +
                "</div>" +
                "<div class=\"form-group\">" +
                "<label class=\"col-md-3 control-label\" for=\"age\">Age</label>" +
                "<div class=\"col-md-9\">" +
                "<input type=\"number\" id=\"age\" class=\"form-control\" placeholder=\"How old are you?\"/>" +
                "</div>" +
                "</div>" +
                "<div class=\"form-group\">" +
                "<label class=\"col-md-3 control-label\" for=\"hobbies\">Hobbies</label>" +
                "<div class=\"col-md-9\">" +
                "<textarea id=\"hobbies\" class=\"form-control\" rows=\"3\"/>" +
                "<span class=\"help-block textarea\">One item per line</span>" +
                "</div>" +
                "</div>" +
                "<div class=\"form-group\">" +
                "<label class=\"col-md-3 control-label\" for=\"choose\">Choose</label>" +
                "<div class=\"col-md-9\">" +
                "<select id=\"choose\" class=\"form-control selectpicker\">" +
                "<option>Lorem</option>" +
                "<option>ipsum</option>" +
                "</select>" +
                "</div>" +
                "</div>" +
                "<div class=\"form-group\">" +
                "<div class=\"col-md-offset-3 col-md-9\">" +
                "<div class=\"pull-right form-buttons\">" +
                "<button class=\"btn btn-default btn-form\">Cancel</button>" +
                "<button class=\"btn btn-primary btn-form\">Save</button>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</form>";

        assertEquals(markup, builder.build().toString());
    }


    // ------------------------------------------------------ mixed content

    @Test
    public void mixed() {
        // @formatter:off
        builder
                .div()
                    .span()
                        .add("br")
                        .div()
                            .add("br")
                            .span()
                                .add("br")
                                .add("br")
                            .end()
                            .add("br")
                        .end()
                        .add("br")
                        .add("br")
                    .end()
                .end();
        // @formatter:on

        assertEquals("<div><span><br/><div><br/><span><br/><br/></span><br/></div><br/><br/></span></div>",
                builder.build().toString());
    }

    @Test
    public void list() {
        // @formatter:off
        builder
                .p().textContent("Some text").end()
                .ol()
                    .li().textContent("one").end()
                    .li().textContent("two").end()
                    .li().textContent("three").end()
                .end()
                .div().textContent("foo").end()
                .add("br");
        // @formatter:on
        Iterable<HTMLElement> elements = builder.elements();
        assertEquals(4, Iterables.size(elements));
        StringBuilder html = new StringBuilder();
        elements.forEach(html::append);
        assertEquals("<p>Some text</p><ol><li>one</li><li>two</li><li>three</li></ol><div>foo</div><br/>",
                html.toString());
    }


    // ------------------------------------------------------ attributes

    @Test
    public void attributes() {
        assertEquals("<div id=\"foo\" title=\"bar\" class=\"bigger\"/>",
                builder.div().id("foo").title("bar").css("bigger").build().toString());
        assertEquals("<div foo=\"bar\"/>",
                builder.div().attr("foo", "bar").end().build().toString());
    }

    @Test
    public void aria() {
        assertEquals("<div aria-one=\"1\" aria-two=\"2\" aria-three=\"3\"/>",
                builder.div().attr("aria-one", "1").aria("two", "2").aria("aria-three", "3").build().toString());
    }

    @Test
    public void data() {
        assertEquals("<div data-one=\"1\" data-two=\"2\" data-three=\"3\"/>",
                builder.div().attr("data-one", "1")
                        .data("two", "2")
                        .data("data-three", "3")
                        .build().toString());
    }


    // ------------------------------------------------------ event listener

    @Test
    public void events() {
        // not a real test - just there to show the API
        builder
                .div()
                .button().on(click, event -> System.out.println("Hit!"))
                .end();
    }


    // ------------------------------------------------------ references

    @Test
    public void reference() {
        builder.div().rememberAs("foo").end();
        assertNotNull(builder.referenceFor("foo"));
    }

    @Test(expected = NoSuchElementException.class)
    public void invalidReference() {
        builder.referenceFor("foo");
    }


    // ------------------------------------------------------ illegal states

    @Test(expected = IllegalStateException.class)
    public void noParentElement() {
        builder.add("br");
    }

    @Test(expected = IllegalStateException.class)
    public void noCurrentElement() {
        builder.id("boom");
    }

    @Test(expected = IllegalStateException.class)
    public void unbalancedEnd() {
        builder.div().add("br").end().end();
    }

    @Test(expected = IllegalStateException.class)
    public void unbalancedBuild() {
        builder.div().span().build();
    }
}
