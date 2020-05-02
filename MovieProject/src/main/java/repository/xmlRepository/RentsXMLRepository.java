package repository.xmlRepository;

import domain.Rent;
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

public class RentsXMLRepository extends InMemoryRepository<Integer, Rent> {

    private String filename;

    public RentsXMLRepository(Validator<Rent> validator, String filename) {
        super(validator);
        this.filename = filename;
        this.loadData();
    }

    private Rent createRentFromElement(Element rentElement) {
        return new Rent(Integer.parseInt(rentElement.getElementsByTagName("id").item(0).getTextContent()), Integer.parseInt(rentElement.getElementsByTagName("movieId").item(0).getTextContent()), Integer.parseInt(rentElement.getElementsByTagName("clientId").item(0).getTextContent()));
    }

    private void loadData() throws ValidatorException {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this.filename);
            Element root = document.getDocumentElement();
            NodeList children = root.getChildNodes();
            IntStream.range(0, children.getLength())
                    .mapToObj(children::item).
                    filter(node -> node instanceof Element).
                    map(node -> createRentFromElement((Element) node)).
                    forEach(super::save);
        } catch (SAXException | ParserConfigurationException | IOException e){
            throw new XMLException(e.getMessage(),e);
        }
    }

    private Node rentToNode(Rent rent, Document document) {
        Element rentElement = document.createElement("rent");

        Element rentIdElement = document.createElement("id");
        rentIdElement.setTextContent(String.valueOf(rent.getId()));
        rentElement.appendChild(rentIdElement);

        Element rentIdMovieElement = document.createElement("movieId");
        rentIdMovieElement.setTextContent(String.valueOf(rent.getMovieId()));
        rentElement.appendChild(rentIdMovieElement);

        Element rentIdClientElement = document.createElement("clientId");
        rentIdClientElement.setTextContent(String.valueOf(rent.getClientId()));
        rentElement.appendChild(rentIdClientElement);

        return rentElement;
    }

    public void saveToFile() {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = document.createElement("movie-project");
            document.appendChild(root);
            StreamSupport.stream(super.findAll().spliterator(), false).forEach(rent -> root.appendChild(rentToNode(rent, document)));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(document), new StreamResult(new File(filename)));
        } catch (Exception e) {
            throw new XMLException(e.getMessage(),e);
        }
    }

    public Optional<Rent> save(Rent entity) throws ValidatorException {
        Optional<Rent> optional = super.save(entity);
        optional.ifPresentOrElse((v) -> {}, () -> {
            try {
                saveToFile();
            } catch (Exception e) {
                throw new XMLException(e.getMessage(),e);
            }
        });
        return optional;
    }

    public Optional<Rent> update(Rent entity) throws ValidatorException {
        Optional<Rent> optional = super.update(entity);
        optional.ifPresentOrElse((v) -> {}, () -> {
            try {
                saveToFile();
            } catch (Exception e) {
                throw new XMLException(e.getMessage(),e);
            }
        });
        return optional;
    }

    public Optional<Rent> delete(Integer id) throws ValidatorException {
        Optional<Rent> optional = super.delete(id);
        optional.ifPresentOrElse((r) -> {
            try {
                saveToFile();
            } catch (Exception e) {
                throw new XMLException(e.getMessage(),e);
            }
            }, () -> {});
        return optional;
    }
}
