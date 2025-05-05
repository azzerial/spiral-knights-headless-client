/*
 * Copyright 2025 Robin Mercier (azzerial)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.azzerial.skhc;

import com.threerings.projectx.data.ProjectXCredentials;
import net.azzerial.skhc.enums.Language;
import net.azzerial.skhc.enums.Region;
import net.azzerial.skhc.events.EventListener;
import net.azzerial.skhc.services.Service;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Used to configure the Spiral Knights related settings and create new {@link SKClient} instances.
 *
 * <p> The configuration of the {@link SKClient} is made in two phases:
 * <ol>
 *     <li>Through the SKClientBuilder, specify which account will be used for the session and configure the game {@link Language language}, server {@link Region region} and {@link Service services} which will be active.</li>
 *     <li>Through the {@link SKClient}, register {@link EventListener event listeners} - which will receive updates from the enabled game {@link Service services} - and handle client session state - through {@link SKClient#connect()} and {@link SKClient#disconnect()} -.</li>
 * </ol>
 *
 * <p><b>Example</b><br>
 * <pre>{@code
 * final SKClient client = SKClientBuilder.create("username", "password")
 *     .setRegion(Region.EU_WEST)
 *     .setLanguage(Language.GERMAN)
 *     .enableServices(Service.EXCHANGE)
 *     .build();
 * }</pre>
 *
 * @see    SKClient
 * @see    #create(String, String)
 * @see    #build()
 * */
public final class SKClientBuilder {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("[a-zA-Z0-9](?:_?[a-zA-Z0-9]){3,11}");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9-]*");

    private final String username;
    private final String password;
    private final EnumSet<Service> services = EnumSet.noneOf(Service.class);

    private Language language;
    private Region region;

    /* Constructors */

    private SKClientBuilder(@NotNull String username, @NotNull String password) {
        this.username = username;
        this.password = password;
    }

    /* Getters & Setters */

    /**
     * Set the {@link Language language} used by the game client.
     *
     * <p><b>Default</b>: {@link Language#ENGLISH}
     *
     * @param language
     *        The {@link Language language} to be used.
     *
     * @return The SKClientBuilder instance, to be used for chaining.
     *
     * @throws NullPointerException
     *         If provided with {@code null}.
     *
     * @see    Language
     */
    @NotNull
    public SKClientBuilder setLanguage(@NotNull Language language) {
        Objects.requireNonNull(language, "Provided language cannot be null");
        this.language = language;
        return this;
    }

    /**
     * Set the {@link Region region} the game client will be connected to.
     *
     * <p><b>Default</b>: {@link Region#US_EAST}
     *
     * <p><b>{@code /!\ IMPORTANT /!\}</b>
     * <br>This setting is currently being bypassed by a hard coded connection IP.
     * <br>More reverse engineering needs to be done for this feature to work.
     *
     * @param region
     *        The {@link Region region} to be connected to.
     *
     * @return The SKClientBuilder instance, to be used for chaining.
     *
     * @throws NullPointerException
     *         If provided with {@code null}.
     *
     * @see    Region
     */
    @NotNull
    public SKClientBuilder setRegion(@NotNull Region region) {
        Objects.requireNonNull(region, "Provided region cannot be null");
        this.region = region;
        return this;
    }

    /**
     * Enable the specified {@link Service services} to be active during the session.
     * <br>This will not disable any currently enabled service.
     *
     * @param service
     *        A {@link Service service} to enable.
     *
     * @param services
     *        Other {@link Service services} to enable.
     *
     * @return The SKClientBuilder instance, to be used for chaining.
     *
     * @throws NullPointerException
     *         If provided with {@code null}.
     *
     * @see    Service
     * @see    #enableServices(Collection)
     * @see    #enableAllServices()
     * @see    #disableServices(Service, Service...)
     */
    @NotNull
    public SKClientBuilder enableServices(@NotNull Service service, @NotNull Service... services) {
        Objects.requireNonNull(service, "Provided service cannot be null");
        Arrays.stream(services)
            .forEach(it -> Objects.requireNonNull(it, "Provided service cannot be null"));
        this.services.addAll(EnumSet.of(service, services));
        return this;
    }

    /**
     * Enable the specified {@link Service services} to be active during the session.
     * <br>This will not disable any currently enabled service.
     *
     * @param services
     *        The {@link Service services} to enable.
     *
     * @return The SKClientBuilder instance, to be used for chaining.
     *
     * @throws NullPointerException
     *         If provided with {@code null}.
     *
     * @see    Service
     * @see    #enableServices(Service, Service...)
     * @see    #enableAllServices()
     * @see    #disableServices(Collection)
     */
    @NotNull
    public SKClientBuilder enableServices(@NotNull Collection<Service> services) {
        Objects.requireNonNull(services, "Provided services collection cannot be null");
        services.forEach(it -> Objects.requireNonNull(it, "Provided services collection cannot contain null elements"));
        this.services.addAll(services);
        return this;
    }

    /**
     * Set all {@link Service services} to be active during the session.
     *
     * @return The SKClientBuilder instance, to be used for chaining.
     *
     * @see    Service
     * @see    #disableAllServices()
     */
    @NotNull
    public SKClientBuilder enableAllServices() {
        this.services.addAll(EnumSet.allOf(Service.class));
        return this;
    }

    /**
     * Disable the specified {@link Service services} to be inactive during the session.
     * <br>This will not enable any currently disabled service.
     *
     * @param service
     *        A {@link Service service} to disable.
     *
     * @param services
     *        Other {@link Service services} to disable.
     *
     * @return The SKClientBuilder instance, to be used for chaining.
     *
     * @throws NullPointerException
     *         If provided with {@code null}.
     *
     * @see    Service
     * @see    #disableServices(Collection)
     * @see    #disableAllServices()
     * @see    #disableServices(Service, Service...)
     */
    @NotNull
    public SKClientBuilder disableServices(@NotNull Service service, @NotNull Service... services) {
        Objects.requireNonNull(service, "Provided service cannot be null");
        Arrays.stream(services)
            .forEach(it -> Objects.requireNonNull(it, "Provided service cannot be null"));
        this.services.removeAll(EnumSet.of(service, services));
        return this;
    }

    /**
     * Disable the specified {@link Service services} to be inactive during the session.
     * <br>This will not enable any currently disabled service.
     *
     * @param services
     *        The {@link Service services} to disable.
     *
     * @return The SKClientBuilder instance, to be used for chaining.
     *
     * @throws NullPointerException
     *         If provided with {@code null}.
     *
     * @see    Service
     * @see    #disableServices(Service, Service...)
     * @see    #disableAllServices()
     * @see    #enableServices(Collection)
     */
    @NotNull
    public SKClientBuilder disableServices(@NotNull Collection<Service> services) {
        Objects.requireNonNull(services, "Provided services collection cannot be null");
        services.forEach(it -> Objects.requireNonNull(it, "Provided services collection cannot contain null elements"));
        this.services.removeAll(services);
        return this;
    }

    /**
     * Set all {@link Service services} to be inactive during the session.
     *
     * @return The SKClientBuilder instance, to be used for chaining.
     *
     * @see    Service
     * @see    #enableAllServices()
     */
    @NotNull
    public SKClientBuilder disableAllServices() {
        this.services.removeAll(EnumSet.allOf(Service.class));
        return this;
    }

    /* Methods */

    /**
     * Create a new {@link SKClientBuilder} instance.
     *
     * <p>You must provide a valid Spiral Knights account to be used for the session.
     * <br>If you do not own one, you can create one by registering on the <a href="https://www.spiralknights.com">Spiral Knights</a> website.
     *
     * @param username
     *        The username of the account to use.
     *
     * @param password
     *        The password of the account to use.
     *
     * @return A new SKClientBuilder instance.
     *
     * @throws NullPointerException
     *         If provided with {@code null}.
     * @throws IllegalArgumentException
     *         If a provided username and/or password does not match the correct character pattern.
     *
     * @see    <a href="https://www.spiralknights.com/register/login.wm">Spiral Knights - Login</a>
     * @see    <a href="https://www.spiralknights.com/register/register.wm">Spiral Knights - Register</a>
     */
    @NotNull
    public static SKClientBuilder create(@NotNull String username, @NotNull String password) {
        Objects.requireNonNull(username, "Provided username cannot be null");
        Objects.requireNonNull(password, "Provided password cannot be null");
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            throw new IllegalArgumentException("Provided username does not match the username pattern: " + USERNAME_PATTERN);
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException("Provided password does not match the password pattern: " + PASSWORD_PATTERN);
        }
        return new SKClientBuilder(username, password);
    }

    /**
     * Build a new {@link SKClient} instance from the configuration - credentials and server settings - of the SKClientBuilder.
     *
     * @return A new {@link SKClient} instance.
     *
     * @see    SKClient
     */
    @NotNull
    public SKClient build() {
        final ProjectXCredentials credentials = new ProjectXCredentials(username, password);

        if (language != null) {
            credentials.language = language.getCode();
        }
        if (region != null) {
            credentials.region = region.getCode();
        }
        return new SKClientImpl(credentials, services);
    }
}
