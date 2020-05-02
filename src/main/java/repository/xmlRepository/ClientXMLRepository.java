package repository.xmlRepository;

import domain.Client;
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

public class ClientXMLRepository extends InMemoryRepository<Integer, Client> {
    private String fileName;

    public ClientXMLRepository(Validator<Client> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    private Client createClientFromElement(Element clientElement) {
        Client client = new Client();
        client.setId(Integer.valueOf(clientElement.getAttribute("id")));
        client.setName(clientElement.getElementsByTagName("name").item(0).getTextContent());
        client.setAge(Integer.parseInt(clientElement.getElementsByTagName("age").item(0).getTextContent()));
        return client;
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
                    .map(node -> createClientFromElement((Element) node))
                    .forEach(super::save);
        } catch (SAXException | ParserConfigurationException | IOException e){
            throw new XMLException(e.getMessage(),e);
        }
    }

    private Node clientToNode(Client client, Document document) {
        Element clientElement = document.createElement("client");

        clientElement.setAttribute("id", client.getId().toString());

        Element titleElement = document.createElement("name");
        titleElement.setTextContent(client.getName());
        clientElement.appendChild(titleElement);

        Element yearElement = document.createElement("age");
        yearElement.setTextContent(String.valueOf(client.getAge()));
        clientElement.appendChild(yearElement);

        return clientElement;
    }

    public void saveToFile() {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = document.createElement("movie-project");
            document.appendChild(root);
            StreamSupport.stream(super.findAll().spliterator(), false).forEach(client -> root.appendChild(clientToNode(client, document)));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(document), new StreamResult(new File(fileName)));
        } catch (Exception e) {
            throw new XMLException(e.getMessage(),e);
        }
    }

    public Optional<Client> save(Client entity) throws ValidatorException {
        Optional<Client> optional = super.save(entity);
        optional.ifPresentOrElse((v) -> {}, () -> {
            try {
                saveToFile();
            } catch (Exception e) {
                throw new XMLException(e.getMessage(),e);
            }
        });
        return optional;
    }

    public Optional<Client> update(Client entity) throws ValidatorException {
        Optional<Client> optional = super.update(entity);
        optional.ifPresentOrElse((v) -> {}, () -> {
            try {
                saveToFile();
            } catch (Exception e) {
                throw new XMLException(e.getMessage(),e);
            }
        });
        return optional;
    }

    public Optional<Client> delete(Integer id) {
        Optional<Client> optional = super.delete(id);
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


