package hexlet.code.controller;

import hexlet.code.dto.LabelDto;
import hexlet.code.model.Label;
import hexlet.code.repository.LabelRepository;
import hexlet.code.service.LabelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

import static hexlet.code.controller.LabelController.LABEL_PATH;
import static hexlet.code.controller.UserController.ID;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("${base-url}" + LABEL_PATH)
@PreAuthorize("isAuthenticated()")
public class LabelController {

    public static final String LABEL_PATH = "/labels";

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private LabelServiceImpl labelService;

    @GetMapping(ID)
    public Label getLabel(@PathVariable long id) {
        return labelRepository.getById(id);
    }

    @GetMapping
    public List<Label> getAllLabels() {
        return labelRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Label createLabel(@RequestBody @Valid LabelDto labelDto) {
        return labelService.createLabel(labelDto);
    }

    @PutMapping(ID)
    public Label updateLabel(
            @PathVariable long id,
            @RequestBody @Valid LabelDto labelDto
    ) {
        return labelService.updateLabel(id, labelDto);
    }

    @DeleteMapping(ID)
    public void deleteLabel(@PathVariable long id) {
        labelService.deleteLabel(id);
    }
}
