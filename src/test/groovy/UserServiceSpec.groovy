import com.bci.auth.utils.JwtUtils
import com.bci.dto.UserDTO
import com.bci.entity.UserEntity
import com.bci.exception.UserAlreadyExistAuthenticationException
import com.bci.mapper.UserMapper
import com.bci.repository.UserRepository
import com.bci.service.UserService
import com.bci.service.impl.UserServiceImpl
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class UserServiceSpec extends Specification {

    UserRepository userRepository = Mock()
    PasswordEncoder passwordEncoder = Mock()
    JwtUtils jwtTokenUtil = Mock()
    UserMapper userMapper = Mock()
    UserService userService = new UserServiceImpl(userRepository, passwordEncoder, jwtTokenUtil, userMapper)

    def "createUser() should create a user successfully"() {
        given:
        def userDTO = new UserDTO(email: "test@example.com", password: "Password123")
        def userEntity = new UserEntity(email: "test@example.com", password: "encodedPassword")
        userRepository.findByEmail(userDTO.email) >> Optional.empty()
        userMapper.toEntity(userDTO) >> userEntity
        passwordEncoder.encode(userDTO.password) >> "encodedPassword"
        jwtTokenUtil.generateToken(userDTO.email) >> "testToken"
        userRepository.save(userEntity) >> userEntity
        userMapper.toDto(userEntity) >> userDTO

        when:
        def createdUser = userService.createUser(userDTO)

        then:
        createdUser.email == userDTO.email
        userDTO.token == "testToken"

    }

    def "createUser() should throw an exception when the user already exists"() {
        given:
        def userDTO = new UserDTO(email: "test@example.com")
        def userEntity = new UserEntity(email: "test@example.com")
        userRepository.findByEmail(userDTO.email) >> Optional.of(userEntity)

        when:
        userService.createUser(userDTO)

        then:
        thrown(UserAlreadyExistAuthenticationException)
    }

    def "findByEmail() should find a user by email successfully"() {
        given:
        def userDTO = new UserDTO(email: "test@example.com")
        def userEntity = new UserEntity(email: "test@example.com")
        userRepository.findByEmail(userDTO.email) >> Optional.of(userEntity)
        userMapper.toDto(userEntity) >> userDTO

        when:
        def foundUser = userService.findByEmail(userDTO.email)

        then:
        foundUser.email == userDTO.email
    }

    def "findByEmail() should throw an exception when the user is not found"() {
        given:
        def userEmail = "test@example.com"
        userRepository.findByEmail(userEmail) >> Optional.empty()

        when:
        userService.findByEmail(userEmail)

        then:
        thrown(UsernameNotFoundException)
    }

    def "updateJwtToken() should update the JWT token successfully"() {
        given:
        def userEmail = "test@example.com"
        def userDTO = new UserDTO(email: userEmail)
        def userEntity = new UserEntity(email: userEmail)
        userRepository.findByEmail(userDTO.email) >> Optional.of(userEntity)
        userMapper.toEntity(userDTO) >> userEntity
        userMapper.toDto(userEntity) >> userDTO
        jwtTokenUtil.generateToken(userEmail) >> "newToken"

        when:
        userService.updateJwtToken(userDTO)

        then:
        userEntity.token == "newToken"
    }
}
