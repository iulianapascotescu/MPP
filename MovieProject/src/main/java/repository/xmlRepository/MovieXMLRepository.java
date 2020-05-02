package repository.xmlRepository;

import domain.Movie;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import domain.validators.XMLException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import repository.InMemoryRepository;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class MovieXMLRepository extends InMemoryRepository<Integer, Movie> {
    private String fileName;

    public MovieXMLRepository(Validator<Movie> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    private Movie createMovieFromElement(Element movieElement) {
        Movie movie = new Movie();
        movie.setId(Integer.valueOf(movieElement.getAttribute("id")));
        movie.setTitle(movieElement.getElementsByTagName("title").item(0).getTextContent());
        movie.setGenre(movieElement.getElementsByTagName("genre").item(0).getTextContent());
        movie.setYear(Integer.parseInt(movieElement.getElementsByTagName("year").item(0).getTextContent()));
        return movie;
    }

    private void loadData() {
        try {
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(this.fileName);
            Element root = document.getDocumentElement();
            NodeList children = root.getChildNodes();
            IntStream.range(0, children.getLength())
                    .mapToObj(children::item)
                    .filter(node -> node instanceof Element)
                    .map(node -> createMovieFromElement((Element) node))
                    .forEach(super::save);
        } catch (SAXException | ParserConfigurationException | IOException e){
            throw new XMLException(e.getMessage(),e);
        }
    }

    private Node movieToNode(Movie movie, Document document) {
        Element movieElement = document.createElement("movie");

        movieElement.setAttribute("id", movie.getId().toString());

        Element titleElement = document.createElement("title");
        titleElement.setTextContent(movie.getTitle());
        movieElement.appendChild(titleElement);

        Element genreElement = document.createElement("genre");
        genreElement.setTextContent(movie.getGenre());
        movieElement.appendChild(genreElement);

        Element yearElement = document.createElement("year");
        yearElement.setTextContent(String.valueOf(movie.getYear()));
        movieElement.appendChild(yearElement);

        return movieElement;
    }

    public void saveToFile() {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = document.createElement("movie-project");
            document.appendChild(root);
            StreamSupport.stream(super.findAll().spliterator(), false).forEach(movie -> root.appendChild(movieToNode(movie, document)));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(document), new StreamResult(new File(fileName)));
        } catch (Exception e) {
            throw new XMLException(e.getMessage(),e);
        }
    }

    public Optional<Movie> save(Movie entity) throws ValidatorException {
        Optional<Movie> optional = super.save(entity);
        optional.ifPresentOrElse((v) -> {}, () -> {
            try {
                saveToFile();
            } catch (Exception e) {
                throw new XMLException(e.getMessage(),e);
            }
        });
        return optional;
    }

    public Optional<Movie> update(Movie entity) throws ValidatorException {
        Optional<Movie> optional = super.update(entity);
        optional.ifPresentOrElse((v) -> {}, () -> {
            try {
                saveToFile();
            } catch (Exception e) {
                throw new XMLException(e.getMessage(),e);
            }
        });
        return optional;
    }

    public Optional<Movie> delete(Integer id) {
        Optional<Movie> optional = super.delete(id);
        optional.ifPresentOrElse((movie) -> {
            try {
                saveToFile();
            } catch (Exception e) {
                throw new XMLException(e.getMessage(),e);
            }
        }, () -> {});
        return optional;
    }
}
