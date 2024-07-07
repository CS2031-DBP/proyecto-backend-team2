package org.example.conectatec.comment.infrastructure;

import org.example.conectatec.TestConectatecApplication;
import org.example.conectatec.comment.domain.Comment;
import org.example.conectatec.clubPost.domain.ClubPost;
import org.example.conectatec.student.domain.Student;
import org.example.conectatec.studentPost.domain.StudentPost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class CommentBoxRepositoryTest extends TestConectatecApplication {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager entityManager;

    private CommentBox commentBox;
    private StudentFeed studentPublication;
    private ClubFeed clubPublication;

    @BeforeEach
    public void setUp() {
        Student student = new Student();
        student.setName("Juan Pérez");
        student.setEmail("juan.perez@example.com");
        student.setPassword("password123");  // Asignar password
        entityManager.persist(student);

        studentPublication = new StudentFeed();
        studentPublication.setHashtag("Publicación Estudiante");
        studentPublication.setStudent(student);
        entityManager.persist(studentPublication);

        clubPublication = new ClubFeed();
        clubPublication.setCaption("Publicación del Club");
        entityManager.persist(clubPublication);

        commentBox = new CommentBox();
        commentBox.setContent("Comentario de Prueba");
        commentBox.setPublication(studentPublication);
        commentBox.setStudent(student);
        commentBox.setClubPublication(clubPublication);
        entityManager.persist(commentBox);
        entityManager.flush();
    }

    @Test
    public void cuandoBuscarPorId_entoncesRetornaComentarioCorrecto() {
        Optional<CommentBox> comentarioEncontrado = commentRepository.findById(commentBox.getId());
        assertTrue(comentarioEncontrado.isPresent());
        assertEquals("Comentario de Prueba", comentarioEncontrado.get().getContent());
    }

    @Test
    public void cuandoBuscarPorPublicacion_entoncesRetornaComentariosCorrectos() {
        List<CommentBox> comentariosEncontrados = commentRepository.findByPublication(studentPublication);
        assertFalse(comentariosEncontrados.isEmpty());
        assertEquals("Comentario de Prueba", comentariosEncontrados.get(0).getContent());
    }

    @Test
    public void cuandoBuscarPorIdInvalido_entoncesComentarioNoEncontrado() {
        Optional<CommentBox> comentarioEncontrado = commentRepository.findById(-1L);
        assertFalse(comentarioEncontrado.isPresent());
    }

    @Test
    public void cuandoEliminarPorId_entoncesComentarioNoPresente() {
        commentRepository.deleteById(commentBox.getId());
        Optional<CommentBox> comentarioEliminado = commentRepository.findById(commentBox.getId());
        assertFalse(comentarioEliminado.isPresent());
    }

    @Test
    public void cuandoGuardar_entoncesRetornaComentarioCorrecto() {
        CommentBox nuevoComentario = new CommentBox();
        nuevoComentario.setContent("Nuevo Comentario");
        nuevoComentario.setPublication(studentPublication);
        nuevoComentario.setStudent(commentBox.getStudent());
        nuevoComentario.setClubPublication(clubPublication);
        CommentBox comentarioGuardado = commentRepository.save(nuevoComentario);
        assertNotNull(comentarioGuardado.getId());
        assertEquals("Nuevo Comentario", comentarioGuardado.getContent());
    }

    @Test
    public void cuandoGuardarYEliminar_entoncesComentarioNoPresente() {
        CommentBox nuevoComentario = new CommentBox();
        nuevoComentario.setContent("Otro Comentario");
        nuevoComentario.setPublication(studentPublication);
        nuevoComentario.setStudent(commentBox.getStudent());
        nuevoComentario.setClubPublication(clubPublication);
        CommentBox comentarioGuardado = commentRepository.save(nuevoComentario);
        assertNotNull(comentarioGuardado.getId());
        commentRepository.deleteById(comentarioGuardado.getId());
        Optional<CommentBox> comentarioEliminado = commentRepository.findById(comentarioGuardado.getId());
        assertFalse(comentarioEliminado.isPresent());
    }
}
