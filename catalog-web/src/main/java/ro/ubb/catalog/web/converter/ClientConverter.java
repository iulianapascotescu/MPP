package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.web.dto.ClientDto;

@Component
public class ClientConverter extends BaseConverter<Client, ClientDto> {
    public ClientConverter() {
    }

    @Override
    public Client convertDtoToModel(ClientDto dto) {
        Client client = new Client(dto.getName(),dto.getAge(), null);
        client.setId(dto.getId());
        return client;
    }

    @Override
    public ClientDto convertModelToDto(Client client) {
        ClientDto clientDto=new ClientDto(client.getName(),client.getAge());
        clientDto.setId(client.getId());
        return clientDto;
    }
}
