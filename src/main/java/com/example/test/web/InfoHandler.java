package com.example.test.web;

import org.cloudfoundry.operations.CloudFoundryOperations;
import org.cloudfoundry.operations.organizations.CreateOrganizationRequest;
import org.cloudfoundry.operations.organizations.OrganizationSummary;
import org.cloudfoundry.operations.spaces.CreateSpaceRequest;
import org.cloudfoundry.operations.spaces.SpaceSummary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class InfoHandler {
	private final Logger log = Loggers.getLogger(InfoHandler.class);
	private final CloudFoundryOperations operations;

	public InfoHandler(CloudFoundryOperations operations) {
		this.operations = operations;
	}

	@SuppressWarnings("unused")
	public Mono<ServerResponse> listOrgs(ServerRequest request) {
		return ServerResponse.ok()
				.contentType(MediaType.TEXT_PLAIN)
				.body(BodyInserters.fromPublisher(getOrgNames(), String.class));
	}

	@SuppressWarnings("unused")
	public Mono<ServerResponse> createAndListOrgs(ServerRequest request) {
		return ServerResponse.ok()
				.contentType(MediaType.TEXT_PLAIN)
				.body(BodyInserters.fromPublisher(createOrg().then(getOrgNames()), String.class));
	}

	@SuppressWarnings("unused")
	public Mono<ServerResponse> listSpaces(ServerRequest request) {
		return ServerResponse.ok()
				.contentType(MediaType.TEXT_PLAIN)
				.body(BodyInserters.fromPublisher(getSpaceNames(), String.class));
	}

	@SuppressWarnings("unused")
	public Mono<ServerResponse> createAndListSpaces(ServerRequest request) {
		return ServerResponse.ok()
				.contentType(MediaType.TEXT_PLAIN)
				.body(BodyInserters.fromPublisher(createSpace().then(getSpaceNames()), String.class));
	}

	private Mono<Void> createOrg() {
		return this.operations.organizations()
				.create(CreateOrganizationRequest.builder()
						.organizationName(UUID.randomUUID().toString())
						.build());
	}

	private Mono<String> getOrgNames() {
		return this.operations.organizations()
				.list()
				.map(OrganizationSummary::getName)
				.doOnNext(spaceName -> log.info("found organization '{}'", spaceName))
				.collect(Collectors.joining(","));
	}

	private Mono<Void> createSpace() {
		return this.operations.spaces()
				.create(CreateSpaceRequest.builder()
						.name(UUID.randomUUID().toString())
						.build());
	}

	private Mono<String> getSpaceNames() {
		return this.operations.spaces()
				.list()
				.map(SpaceSummary::getName)
				.doOnNext(spaceName -> log.info("found space '{}'", spaceName))
				.collect(Collectors.joining(","));
	}
}
